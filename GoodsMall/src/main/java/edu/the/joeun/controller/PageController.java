package edu.the.joeun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/member/add")
    public String memberAddPage(){
        /*
         return "/member/add";
         * resources/templates 에서
         * member 폴더 안에 add.html 을 선택
         */
        return "memberAdd";
    }

}
