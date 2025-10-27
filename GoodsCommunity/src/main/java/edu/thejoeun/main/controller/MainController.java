package edu.thejoeun.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// api 주소와 .html 연결을 작성하는 공간
@Controller
public class MainController {
    /**
     * http://localhost:8080/sign 으로 접속하면
     * @return pages 폴더 내부에 존재하는 signup.html 화면이 보임
     */
    @GetMapping("/sign")
    public String pageSignUp(){
        return "pages/signup";
    }

    @GetMapping("/board")
    public String pageBoard(){
        return "pages/board/boardList";
    }
}