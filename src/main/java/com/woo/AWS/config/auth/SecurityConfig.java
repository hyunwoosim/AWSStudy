package com.woo.AWS.config.auth;

import com.woo.AWS.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화

public class SecurityConfig extends  WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            // h2-console 화면을 사용하기 위해 해당 옵션들을 disable(비활성화)한다.
            .and()
            .authorizeRequests()
            // URL별 권한 관리를 설정하는 옵션의 시작점이다.
            // 선언되어야만 antMatchers 옵션을 사용 가능하다.
            .antMatchers("/", "/css/**", "/images/**", "js/**", "/h2-console/**")
            // 권한 관리 대상을 지정하는 옵션
            // URL, HTTP 메소드별 관리가 가능하다.
            // "/" 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었습니다.
            // "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 했다.
            .permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated()
            // 설정된 값들 이외 나머지 URL들을 나타낸다
            // 여기서 authenticate()을 추가하여 나머지 URL들을 모두 인증된 사용자들에게만 허용하게    한다.
            // 인증된 사용자 ==>> 로그인한 사용자
            .and().oauth2Login().defaultSuccessUrl("/", true)
            // 로그인을 성공하면 자바스크립트로 리다이렉트되는 오류가 있었는데 코드를 추가하여 본 화면으로 나오게 만들었다.
            .and()
            .logout()
            .logoutSuccessUrl("/")
            // 로그 아웃 성공시 "/" 주소로 이동한다.
            .and()
            .oauth2Login()
            // OAuth 2 로그인 기능에 대한 설정의 진입점
            .userInfoEndpoint()
            // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
            .userService(customOAuth2UserService);
            // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.
            // 소셜 서비스들에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능
    }
}

