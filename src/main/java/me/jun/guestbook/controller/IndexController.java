package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.PostResponseDto;
import me.jun.guestbook.dto.PostsRequestDto;
import me.jun.guestbook.dto.PostsResponseDto;
import me.jun.guestbook.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;

    @GetMapping("/index/{page}")
    public String index(
            Model model,
            PostsRequestDto page) {

        postService.readPostByPage(page)
                .getPostInfoDtoPage()
                .map(postResponseDto ->
                        model.addAttribute("list", postResponseDto));

        return "/index";
    }
}