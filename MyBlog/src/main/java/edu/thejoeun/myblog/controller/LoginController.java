package edu.thejoeun.myblog.controller;

import edu.thejoeun.myblog.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    // MemberService 는 interface
    // MemberServiceImpl 는 class
    // 회사에서 백엔드 대리 이하의 직급 회사원이 해야하는 작업
    // 주로 서비스 내부에 존재하는 로직 수정 / 추가
    // A 회사원 -> 로그인 기능. B 회사원 -> 정보 수정
    // C 회사원 -> 포인트 결제
    // 대리 이상의 직급들은 interface 로 기능에 대한 명칭과 자료형 + 매개변수 설정

    @Autowired
    MemberServiceImpl memberServiceImpl;

}
