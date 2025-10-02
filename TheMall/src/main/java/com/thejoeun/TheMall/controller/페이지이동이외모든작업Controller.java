package com.thejoeun.TheMall.controller;


import com.thejoeun.TheMall.model.Goods;
import com.thejoeun.TheMall.model.Users;
import com.thejoeun.TheMall.service.GoodsService;
import com.thejoeun.TheMall.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 데이터 조회, 데이터 저장, 데이터 수정, 데이터 삭제

@CrossOrigin
@RestController
public class 페이지이동이외모든작업Controller {
    // 서비스
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UsersService usersService;
    // 서비스에 관련된 주소 작성

    /*
    ResponseBody :  Java 객체를 JSON 형태로 변환해서 클라이언트 응답
    RestController 어노테이션은 이 기능이 존재해서 굳이 추가로 작성하지 않아도 됨

    Controller 에서 부분적으로 JSON 형태 변환이 필요할 경우 사용

    =============================================================================

    RequestBody
    클라이언트가 보낸 JSON 형태의 데이터를 JAVA 형태로 변환
    POST, PUT 요청을 DB 로 저장, 수정할 때 주로 사용
    */

    // js 에서 데이터 등록하는 fatch promise axios ajax 와 동일한
    // 주소를 가져야 한다.
    @PostMapping("/api/goods")
    public void insertGoods(@RequestBody Goods goods){
        goodsService.insertGoods(goods);
    }

    @GetMapping("/api/goods")
    public List<Goods> getAllGoods(){
        return goodsService.getAllGoods();
    }

    @PostMapping("/api/users")
    public void insertUsers(@RequestBody  Users users){
        usersService.insertUsers(users);
    }

    @GetMapping("/api/usersList")
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

}
