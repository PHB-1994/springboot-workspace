package edu.the.joeun.controller;

import edu.the.joeun.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginAPIController {

    @Autowired
    private UserService userService;

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String login(HttpSession session,
                        @RequestParam String email,
                        @RequestParam String password,
                        Model model){
        return userService.processLogin(email, password, session, model);
    }
}
