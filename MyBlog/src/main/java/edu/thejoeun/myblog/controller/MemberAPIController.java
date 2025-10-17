package edu.thejoeun.myblog.controller;

import edu.thejoeun.myblog.model.Member;
import edu.thejoeun.myblog.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 백엔드가 프론트엔드로 데이터 제대로 보내는지 확인용 컨트롤러
// 프론트엔드 요청사항을 전달하는 컨트롤러
@RestController
public class MemberAPIController {

    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("/api/memberList")
    public List<Member> getMemberList() {
        return memberService.selectMemberList();
    }
}
