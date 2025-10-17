package edu.thejoeun.myblog.service;

import edu.thejoeun.myblog.model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

public interface MemberService {

    /**
     * 로그인을 위한 email 실행
     */
    List<Member> selectMemberList();
    String login(String memberEmail, String memberPassword, HttpSession session, Model model);
    Member getMemberById(String memberId);
    void saveMember(Member member);
}
