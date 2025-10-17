package edu.thejoeun.myblog.controller;

import edu.thejoeun.myblog.model.Member;
import edu.thejoeun.myblog.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 백엔드가 프론트엔드로 데이터 제대로 보내는지 확인용 컨트롤러
// 프론트엔드 요청사항을 전달하는 컨트롤러
@RestController
public class MemberAPIController {

    @Autowired
    private MemberServiceImpl memberService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // 백엔드에서 데이터가 바로 html 파일로 전송
    @GetMapping("/api/memberList")
    public List<Member> getMemberList() {
        return memberService.selectMemberList();
    }

    /**
     * Model model = login 에서만 사용 ~~ 다른데에서 안씁니다. ^0^
     * myPage 로
     * member 명칭을 활용해서 백엔드 확인 없어
     * 프론트엔드로 바로 데이터 전송하는 방법
     * 3년 이하 추천하지 않음
     * 고전 방식
     * 현업에서 많이 사용 안함

    @GetMapping("/api/member/{id}")
    public String getMemberById(@PathVariable int id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "myPage";
    }
     */
    /**
     * 백엔드에서 데이터를 제대로 조회하고 있는지 확인하는 로직
     * 리액트 - 뷰 - 앵귤러 - js 소통에서도 모두 이 방식 활용
     *
     * http://localhost:8080/api/member/1
     *
     *관리자가 모든 유저 리스트에서
     * 유저 한명을 선택하여 상세보기로 이동할 때 사용
     *
     * @param id @PathVariable 주소창에 값으로 넣을 변수 명칭이나 데이터
     * @return select * from 테이블 where id = #{id}
     *         id 값으로 DB 에서 조회된 데이터를 백엔드로 전달
     */
    @GetMapping("/api/member/{id}")
    public Member getMemberById(@PathVariable int id) {
        return memberService.getMemberById(id);
    }

    /**
     * 마이페이지에서 본인의 정보를 조회할 때 사용
     *
     * 추가적으로 session 정보와 session 정보를 전달할
     * Model model 을 활용하여 전송할 예정!
     * @param id
     * @return
     */
    @GetMapping("/api/mypage")
    public Member getMemberByMyPage(int id) {
        return memberService.getMemberById(id);
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
