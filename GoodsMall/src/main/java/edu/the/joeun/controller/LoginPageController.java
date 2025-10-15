package edu.the.joeun.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    /**
     * @param session : static 으로 저장되어 있는 session 에
     *                  로그인 정보가 존재하는지 가져오기
     *
     * @return  만약에 loginUser 에 존재하는 유저가 없음
     *                  -> 로그인 페이지로 이동
     *                  현재 로그인된 상태에서 다시 login 페이지로 오려 한다면
     *                  메인페이지로 돌려보내기
     */
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if(session.getAttribute("loginUser") != null){
            return "redirect:/";
        }
        return "/login";
    }
}
