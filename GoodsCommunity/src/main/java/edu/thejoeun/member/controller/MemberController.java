package edu.thejoeun.member.controller;

import edu.thejoeun.common.util.SessionUtil;
import edu.thejoeun.member.model.dto.Member;
import edu.thejoeun.member.model.service.MemberService;
import edu.thejoeun.member.model.service.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// @SessionAttributes({"loginUser"}) SessionUtil SessionAttributes 동시에 유저 정보 저장
@Controller
@Slf4j // log 를 출력하게 해주는 어노테이션
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;
    /*
    Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'memberController' method
    edu.thejoeun.member.controller.MemberController#pageMain()
    to {GET [/]}: There is already 'mainController' bean method
    edu.thejoeun.main.controller.MainController#pageMain() mapped.

    @GetMapping("/")
    public String pageMain(){
        // return "main";
        return "index";
    }
     */

    // 쿠키 설정할 때 아이디 저장 안되면 가장먼저하는 작업
    // @CookieView 와 Model 은 필요 없음!!!
    @GetMapping("/login")
    public String pageLogin(
    ){
        return "pages/login";
    }

    @GetMapping("/member/myPage")
    public String getMyPage(){
        return  "pages/myPage";
    }


    // GPT or AI 경우 Model 로 모든 것을 처리함
    // Model 과 RedirectAttributes 구분해서 결과값을 클라이언트 전달
    @PostMapping("/login")
    public String login(@RequestParam String memberEmail,
                        @RequestParam String memberPassword,
                        @RequestParam(required = false) String saveId, // 필수로 전달하지 않아도 되는 매개변수
                        HttpSession session,
                        HttpServletResponse res,
                        Model model,
                        RedirectAttributes ra){
        Member member = memberService.login(memberEmail, memberPassword);
        if(member == null){
            ra.addFlashAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/login"; // 일치하지 않는게 맞다면 로그인 페이지로 돌려보내기
        }

        model.addAttribute("loginUser",member);

        // 세션에 로그인 정보를 저장. 이 방법을 쓰게 되면 매번 로그인 정보를 코드마다 세팅해야함. 그래서 안씀
        // session.setAttribute("loginUser", member); - 해당 코드 안씀
        SessionUtil.setLoginUser(session, member);

        // 쿠키에 사용자 정보 저장 (보안상 민감하지 않은 부분만 저장)

        Cookie userIdCookie = new Cookie("saveId", memberEmail);
        userIdCookie.setPath("/");
        // 유저 아이디를 아이디 저장이 체크되어 있으면 30일간 유저 아이디 저장
        // 체크가 안되어 있으면 유저 아이디를 쿠키에 저장하지 않겠다
        /*
        체크박스에서 value 가 없을 때
        - 체크가    된 경우 : on
        - 체크가 안 된 경우 : null
        아이디 저장과 같이 단순 체크는 on - null 을 이용해서 체크 유무를 확인
        아이디를 작성 안했는데 쿠키에 저장할 이유가 없으므로 아이디 값을 작성하고 아이디 저장 체크를 했을 경우만
        30일 동안 아이디 명칭을 저장하겠다
         */

        // userIdCookie != null
        /* 문자열1.equals(문자열2)
        String 내부에 작성되어 있는 메서드 .equals() 는
        맨 앞에 있는 문자열1이 문자열이면 일 때를 기준으로 만들어진 메서드

        문자열.equals(null) 일 때는 false 를 반환하여 에러를 일으키지 않도록 되어 있음

        saveId 가 null 값으로 전달될 경우에는

        null.equals("문자열") 일 때는 대비해놓은 코드가 없음

        saveId 에서 체크된 값으로 전달될 경우에는
        "on".equals("on") 형태로 전달되어 true 값을 반환하도록 기능 내부적으로 설정

         * if (saveId.equals("on")){
         * if ("on".equals(saveId)){
         */
        if ("on".equals(saveId)){
            userIdCookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 30일 단위 추가
        } else {
            userIdCookie.setMaxAge(0); // 쿠키 삭제
        }
        res.addCookie(userIdCookie); // 응답 객체에 쿠키 추가 -> 클라이언트 전달

        Member des = (Member) session.getAttribute("destination");
        log.info("destination : {}", des);
        Member logUser = (Member) session.getAttribute("loginUser");
        log.info("loginUser : {}", logUser);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse res){
        SessionUtil.invalidateLoginUser(session);

        /* 로그아웃 시 아이디 저장되어 있는 saveId 도 삭제된다.
        Cookie userIdCookie = new Cookie("saveId", null);
        userIdCookie.setMaxAge(0);
        userIdCookie.setPath("/");
        res.addCookie(userIdCookie);
         */

        return "redirect:/"; //로그아웃 선택시 모든 쿠키 데이터 지우고 메인으로 돌려보내기
    }
}