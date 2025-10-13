package edu.the.joeun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
* src/main/resource/templates/ 아래에 존재하는
* .html 형식의 화면을 볼 수 있도록 설정하는 Controller
* .html 보일 화면 위치를 /api/endpoint 를 이용하여 설정
*
* api      = 모든 주소들 나타내는 용어
* endpoint = 각 개별 주소를 나타내는 용어
*
* @Controller : 웹 요청을 처리하는 컨트롤러
* */
@Controller
public class 페이지이동Controller {

    /**
     * URL("/")로 접속했을 때 index.html 페이지를 반환
     * @return templates/index.html 파일의 이름 (확장자는 제외)
     *
     * 보통 templates 내부에는 .html 파일 밖에 존재하지 않기 때문에 확장자를 제외하지만
     *
     * static 내부에는 .html .css .js .. 등 다양한 파일이 존재하기 때문에 확장자를 붙여줌
     */
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    /**
     * URL("/goodList")로 접속했을 때 goodsList.css.css.html 페이지를 반환
     * @return templates/goodsList.css.css.html 파일의 이름 (확장자는 제외)
     */
    @GetMapping("/goodList")
    public String getGoodsList(){
        return "goodsList";
    }

    /**
     * URL("/goodsAdd.css")로 접속했을 때 goodsAdd.css.html 페이지를 반환
     * @return templates/goodsAdd.css.html 파일의 이름 (확장자는 제외)
     */
    @GetMapping("/goodsAdd")
    public String getGoodsAdd(){
        return "goodsAdd";
    }

    @GetMapping("/user/list")
    public String getUsersList(){
        return "사용자목록";
    }

    @GetMapping("/user/add")
    public String getUsersAdd(){
        return "사용자등록";
    }
}








