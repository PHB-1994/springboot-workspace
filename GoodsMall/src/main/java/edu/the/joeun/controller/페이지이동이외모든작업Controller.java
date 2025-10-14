package edu.the.joeun.controller;

import edu.the.joeun.model.Goods;
import edu.the.joeun.model.Member;
import edu.the.joeun.model.User;
import edu.the.joeun.service.GoodsService;
import edu.the.joeun.service.MemberService;
import edu.the.joeun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController = @RequestMapping + Controller 
 *                   .html 페이지 이동이 아니라 데이터에 대한 작업을 진행할 때 사용하는 어노테이션
 *                   데이터를 JSON 형태로 html 로 전달하거나,
 *                   특정 데이터를 JSON 형태로 조회하거나,
 *                   데이터 저장, 수정, 삭제와 같은 작업 진행하는 문서 작업임을 표기
 *                   
 * @RestController = 한 Java 문서 페이지에서 전체적으로 공통 api 앞 주소를 설정하고,
 *                   공통된 주소를 모두 사용할 때 주로 설정
 *                   특정 api 주소로 백엔드 데이터를 전달할 때 사용
 *                   
 * 프론트엔드와 백엔드는 서로 어디서 어떤 데이터를 주고 받을 지 주소에 대한 약속 기약하고,
 * 프론트엔드는 ajax 나 fetch, promise 내에서 백엔드로 데이터를 전달하거나 조회할 주소 작성
 *     백엔드는 @RestController 내부에서 @GetMapping 이나 @PostMapping 등
 *             각 작업에 걸맞는 어노테이션 Mapping 주소를 설정하고
 *             각 주소에 걸맞게 프론트엔드와 상호 작용 할 수 있도록 설정
 *             -> 현재는 config 작업을 하기 전이기 때문에 @CrossOrigin() 을 설정
 *                프로젝트 작업할 때 @CrossOrigin() 작업한 사람은 경고 1회를 받을 정도로
 *                반복적인 사용 자제하기!
 *                
 * @CrossOrigin() : 프론트엔드 웹사이트에서 백엔드가 허용한 웹사이트만 SQL 에 저장된 데이터를 사용할 수
 *                  있도록 부분적으로 허용하는 어노테이션
 *                  허용하는 방법은 무수히 많음 / 방법을 통일해서 하나로 사용하지 않으면
 *                  프론트엔드 권한 허용이 중구난방이 되어 사이트 자체가 접속되지 않는 현상 발생
 */
@CrossOrigin("*") // 나중에 설정할 때는 원하는 주소와 포트만 설정할 수 있음
@RestController
public class 페이지이동이외모든작업Controller {
    // @Service 와 그에 해당하는 주소 설정
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService usersService;

    @Autowired
    private MemberService memberService;

    /**
     * 모든 상품 목록을 조회하는 API
     * [프론트엔드에서 SQL에 저장된 데이터를 JAVA 통해서 호출하는 방법]
     * fetch("http://localhost:8080/api/goods")
     *
     * [처리 흐름]
     * 1. 프론트엔드에서 GET 요청으로 "/api/goods" 호출
     * 2. Controller 가 요청을 받아 GoodsService 의 getAll() 메서드 호출
     * 3. Service 는 GoodsMapper 를 통해 DB 에서 모든 상품 데이터를 조회
     *    -> goodsMapper.getAll() 실행하여 @Mapper 에서 id 값으로 SQL 구문 호출
     * 4. 조회된 List<Goods> 데이터를 JSON 형태로 변환하여 프론트엔드에 전달
     *
     * @return List<Goods> - 데이터베이스에 저장된 모든 상품 목록
     *                       (상품 번호, 상품명, 가격, 설명 등의 정보를 담은 Goods 객체 리스트 사용)
     *
     ===========================================================================================
     * @Service 내부에서 작성한 return 반환 자료형을 Controller 에서도 그대로 사용해도 되고,
     *          개발자가 원하는 형태로 변환해서 사용 가능
     *      서비스 내부에 작성된 getAll() 기능의 로직
     *      public List<Goods> getAll(){
     *          return goodsMapper.getAll();
     *     }
     *
     */
    @GetMapping("/api/goods")
    public List<Goods> getAll(){
        return goodsService.getAll();
    }


    /**
     * 상품을 등록하는 API<br/>
     *
     * [프론트엔드 호출 방법]<br/>
     * fetch('http://localhost:8080/api/goods',<br/>
     *
     * [처리 흐름]<br/>
     * 1. 프론트엔드에서 Post 요청으로 "/api/goods" 호출(클라이언트가 작성한 상품 정보를 JSON 형태로 전달)
     * 2. Controller 가 요청을 받아 JSON 데이터를 Goods 객체로 자동 변환
     *    -> @RequestBody 를 이용해서 JSON 데이터를 Java 형태로 맞춰서 변환해주는 어노테이션
     * 3. GoodsService 의 insertGoods() 메서드 호출
     * 4. Service 는 GoodsMapper 를 통해 DB 에 상품 데이터 저장
     *    -> goodsMapper.insertGoods(goods) 실행하고, id 가 insertGoods 인 MyBatis SQL 구문 실행된
     * 5. INSERT 쿼리 실행 후 성공 / 실패 응답(반환값 없음)
     *
     * @param goods - 등록할 상품정보를 담은 Goods 객체
     *                JavaScript 에 저장된 데이터를 Goods 객체로 전달 받은 상태
     * @retrun void - 반환값 없으며, 저장 성공 시 (HTTP 200 OK 상태 코드만 전달)
     *
     * -> 개발자의 코드가 문제가 생겼을 경우 AI 에서는 상태 코드 확인을 추가하라 할 것임
     *    경고 1회, 상태 코드 추가할 필요가 현재는 없으므로, 팀원에게 요청하거나 대화를 통해 해결하기
     *    ResponseEntity.ok 와 같은 구문 사용 금지
     */
    @PostMapping("/api/goods")
    public void insertGoods(@RequestBody Goods goods){
        goodsService.insertGoods(goods);
    }


    /**
     * 유저 정보를 등록하는 API
     * /api/users -> Post 일 경우 유저를 등록하는 공간으로 유저 등록 진행 시작
     * @param user = html -> js 로 가져온 유저 정보 데이터를 모두 json 형태로 설정
     *              @RequestBody 를 이용해서 js 형태 -> json 형태 설정
     */
    @PostMapping("/api/users")
    public void insertUsers(@RequestBody User user){
        usersService.insertUser(user);
    }

    /**
     * /api/users -> get 일 경우 모든 유저를 조회하는 공간으로 유저 조회 진행 시작
     * 단순히 js -> html 로 유저 데이터를 SQL 에서 전달하는 것이기 때문에
     * 매개변수와 파라미터는 빈 값 형태
     * @return MyBatis 에 작성된 SQL -> mapper 조회 후, service 로 전달해온 데이터를
     *         js -> html 로 반환하여 클라이언트가 모든 유저 목록을 조회할 수 있도록 설정
     */
    @GetMapping("/api/users")
    public List<User> getAllUser(){
        return usersService.getAllUser();
    }

    @PostMapping("/api/member/add")
    public void insertMember(@RequestBody Member member){
        memberService.insertMember(member);
    }

}
