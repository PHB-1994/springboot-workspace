package edu.thejoeun.myblog.common.cofig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 환경설정 이라는 어노테이션
// Springboot 처음시작할 때 @Configuration 으로 작성되어 있는
// 환경설정을 가장 먼저 프로젝트에서 세팅
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 유저 인터셉터 관리자 인터센터로 구분지어서
    // 로그인한 유저가 들어갈 수 있는 url
    // 관리자   유저가 들어갈 수 있는 url
    // 비로그인 유저가 접속할 수 있는 url 설정




    // webconfig  설정에 대해 권장할 때 --> HELP 요청하기!!!!


    
    // templates 나 view 폴더에서 html jsp 를 따로 시작할 때는
    // 백엔드에 데이터 조회, 수정, 추가, 삭제 와 같은 권한을 url 별로 따로 추가
    // controller 에서 @CrossOrigin("*") 과 같은 설정이 필요없음
    // 나중에 추가적으로 filterChain 메서드 추가할 것
    // 리액트와 작업할 때 리액트 서버/포트 관련 추가 설정 필요
    // 지금은 springboot 자체에서 html 이나 jsp를 실행할 것이기 때문에
    // 현재는 필요하지 않음
    // file:///D:/springboot-workspace/springboot-workspace/GoodsMall/src/main/resources/templates/memberAdd.html
}
