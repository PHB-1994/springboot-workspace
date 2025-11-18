package edu.thejoeun.member.controller;

import edu.thejoeun.common.util.SessionUtil;
import edu.thejoeun.member.model.dto.Member;
import edu.thejoeun.member.model.service.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberServiceImpl memberService;

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody Map<String, String> loginData, HttpSession session){

        String memberEmail = loginData.get("memberEmail");
        String memberPassword = loginData.get("memberPassword");

        Map<String, Object> res = memberService.loginProcess(memberEmail, memberPassword, session);

        return res;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session){
        return memberService.logoutProcess(session);
    }

    /**
     * 로그인 상태 확인 API
     * React 앱이 시작될 때 호출
     * @param session
     * @return 로그인 상태 반한
     */
    @GetMapping("/check")
    public Map<String, Object> checkLoginStatus(HttpSession session){
        return memberService.checkLoginStatus(session);
    }

    // const res = axios.post("/api/auth/signup",signupData);
    // PostMapping 만들기
    // mapper.xml -> mapper.java -> service.java -> serviceImpl.java apiController.java
    @PostMapping("/signup")
    public void signup(@RequestBody Member member) {
        log.info("=== 회원가입 요청===");
        log.info("요청 데이터 - 이름 : {}, 이메일{}",member.getMemberName(),member.getMemberEmail());

        try{
            memberService.saveMember(member);
            log.info("회원가입 성공 - 이메일 : {}",member.getMemberEmail());
        } catch (Exception e){
            log.error("회원가입 실패 - 이메일 : {}, 에러 : {}", member.getMemberEmail(), e.getMessage());
        }
    }

}

