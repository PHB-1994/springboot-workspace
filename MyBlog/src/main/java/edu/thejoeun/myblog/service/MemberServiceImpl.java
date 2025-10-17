package edu.thejoeun.myblog.service;

import edu.thejoeun.myblog.mapper.MemberMapper;
import edu.thejoeun.myblog.model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Transactional
/*
해당 클래스 종료 시 까지 예외가 발생하지 않으면 commit
                        예외가 발생하면        rollback
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> selectMemberList() {
        // 유저 리스트 조회
        return memberMapper.selectMemberList();
    }

    @Override
    public String login(String memberEmail, String memberPassword, HttpSession session, Model model) {
        Member member = memberMapper.getMemberByEmail(memberEmail);

        if(member == null){
            model.addAttribute("error","not found email");
            return "login";
        }

        member.setMemberPassword(null);
        // sessionUtil
        return "redirect:/"; // 이 상태로 다시 메인페이지로 이동 redirect: 어디로 다시 이동하자
    }

    public void logout(HttpSession session){
        // 로그아웃 세션 추가
    }

    @Override
    public Member getMemberById(String memberId) {
        return null;
    }

    @Override
    public void saveMember(Member member) {
        memberMapper.saveMember(member);
    }
}