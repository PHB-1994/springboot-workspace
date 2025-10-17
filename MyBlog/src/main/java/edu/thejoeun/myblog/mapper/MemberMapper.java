package edu.thejoeun.myblog.mapper;

import edu.thejoeun.myblog.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    /**
     * 로그인을 위한 email 실행
     */
    Member getMemberByEmail(String memberEmail);
    Member getMemberById(int memberId);
    List<Member> selectMemberList();
    void saveMember(Member member);
}
