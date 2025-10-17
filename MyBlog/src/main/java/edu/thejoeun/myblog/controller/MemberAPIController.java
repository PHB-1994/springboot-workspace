package edu.thejoeun.myblog.controller;

import edu.thejoeun.myblog.model.Member;
import edu.thejoeun.myblog.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 백엔드가 프론트엔드로 데이터 제대로 보내는지 확인용 컨트롤러
// 프론트엔드 요청사항을 전달하는 컨트롤러
@RestController
public class MemberAPIController {

    @Autowired
    private MemberServiceImpl memberService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/api/memberList")
    public List<Member> getMemberList() {
        return memberService.selectMemberList();
    }



    @PostMapping("/api/member/register")
    public void saveMember(@ModelAttribute Member member) {
        // 암호화 설정 비밀번호 저장
        // html -> controller 로 가져온 데이터 중에서 멤버 비밀번호만 가져오기
        // 가져온 비밀번호를 암호화 처리해서 다시 비밀번호 공간에 넣어놓기
        member.setMemberPassword(bCryptPasswordEncoder.encode(member.getMemberPassword()));
        // 모든 작업이 끝난 데이터를 DB 에 저장하기
        memberService.saveMember(member);
    }
}
