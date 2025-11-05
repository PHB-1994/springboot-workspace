package edu.thejoeun.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// api 주소와 .html 연결을 작성하는 공간
@Controller
public class MainController {

    @GetMapping("/")
    public String pageMain(){
        return "index";
    }

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
        return "pages/board/boardList.html";
    }

    // 상세보기 페이지
    // @GetMapping("/board/detail/{id} http://localhost:8080/board/detail/id=1
    // 상세보기는 ? 쿼리형태의 http://localhost:8080/board/detail?id=1
    // id 를 읽어서 게시물 조회
    // @GetMapping("/board/detail/id={id}")
    // public String pageBoardDetail(@PathVariable int id){
    @GetMapping("/board/detail")
    public String pageBoardDetail(@RequestParam int id){
        return "pages/board/boardDetail.html";
    }
}