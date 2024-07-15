package com.woo.AWS.web;

import com.woo.AWS.config.auth.dto.SessionUser;
import com.woo.AWS.domain.service.posts.PostsService;
import com.woo.AWS.web.dto.PostsResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findALlDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // 로그인 성공시 SeesionUser를 저장하도록 구성하였다.
        // 로그인 성공시 http.Session.getAttribute("user")에서 값을 가져올 수 있다.

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
