package com.woo.AWS.config.auth;

import com.woo.AWS.config.auth.dto.OAuthAttributes;
import com.woo.AWS.config.auth.dto.SessionUser;
import com.woo.AWS.domain.user.User;
import com.woo.AWS.domain.user.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    // 소셜 로그인 이후 가져온 사용자의 정보들을 기반으로 가입, 정보수정, 세션 저장 기능 지원

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
            .getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint()
            // 현재 로그인 진행중인 서비스를 구분하는 코드이다.
            // 네이버,구글 구분을 위한 코드
            .getUserNameAttributeName();
        // OAuth2 로그인 진행 시 키가되는 필드값이다.(Primary Key와 같은 역할)

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
            oAuth2User.getAttributes());
        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스이다.

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        // 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
            attributes.getAttributes(),
            attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
            .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
