package edu.thejoeun.member.model.service;


import edu.thejoeun.member.model.dto.Member;
import edu.thejoeun.member.model.mapper.MemberMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MemberServiceImpl  implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    /**
     * Autowired 형태로 변경
     * config에서 관리할 것
     */
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    /**
     * MemberService 에 작성한 기능명칭(매개변수 자료형 개수) 가 <br/>
     * MemberServiceImpl 과 일치하지 않으면 @Override 된 상태가 아님 <br/>
     * 명칭만 똑같이 썼을 뿐 <br/>
     * @param memberEmail    html -> js -> controller api/endpoint 로 가져온 이메일
     * @param memberPassword html -> js -> controller api/endpoint 로 가져온 비밀번호
     * @return
     */
    @Override
    public Member login(String memberEmail, String memberPassword) {
        Member member = memberMapper.getMemberByEmail(memberEmail);
        // email 로 db 에서 조회되는 데이터가 0개 인게 사실이라면
        if(member == null) {
            return null;
        }

        // 비밀번호 일치하지 않은게 사실이라면 null
        // bc 크립토의 경우 알고리즘
        // 클라이언트가 작성한 비밀번호 -> bcrypt 형태의 알고리즘으로 변환
        // DB에서 bcrypt 형태로 변환된 비밀번호를 가져오는 위치
        //                                클라이언트가 작성한 비밀번호, db에 저장된 비밀번호
        if(!bCryptPasswordEncoder.matches(              memberPassword, member.getMemberPassword())) {
            return null;
        }

        // 이메일 존재하며, 비밀번호도 일치한다면
        member.setMemberPassword(null); // 비밀번호 제거한 상태로 유저정보를 controller 전달
        return member; // 멤버에 대한 모든 정보를 컨트롤러에 전달
    }
}
