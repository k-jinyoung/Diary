package com.codehows.diary.controller;

import com.codehows.diary.dto.AddUserRequest;
import com.codehows.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request);      //회원가입 메소드 호출
        return "redirect:/login";       //회원가입이 완료된 이후에 로그인 페이지로 이동
    }

    //로그아웃
    /*@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }*/
}
