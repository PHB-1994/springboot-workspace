package edu.thejoeun.myblog.controller;

import edu.thejoeun.myblog.model.Member;
import edu.thejoeun.myblog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// @RestController ReactJs 할 때 까지 안녕 ~!
// 백엔드 로직 작업 우선 진행할 때 필요할 수 있다.
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return  "index";
    }

    // 백엔드에서 데이터가 바로 html 파일로 전송
    @GetMapping("/member/list")
    public String getMemberPage(Model model){
        List<Member> members = memberService.selectMemberList();
        model.addAttribute("members",members);
        return "member";
    }

    @GetMapping("/member/register")
    public String getMemberRegisterPage() {
        return "member_register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/member/{id}")
    public String getMemberPage(@PathVariable int id, Model model) {
        return "member-detail";
    }

    @GetMapping("/member/myPage")
    public String getMyPage() {
        return "myPage";
    }
}
