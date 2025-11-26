package edu.thejoeun.member.model.mapper;

import edu.thejoeun.member.model.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member getMemberByEmail(String memberEmail);

    /*
    데이터 저장 후 저장된 멤버 데이터 내용 조회할 때
    Member saveMember(Member member);

    0 과 1 을 기준으로 데이터 자장 성공 여부
    0 과 1 이 아닌 수가 나오면 데이터가 N 개 만큼 저장된 상태 확인
    주로 상품 여러 개 등록, 게시물 여러 개 등록할 때
    int saveMember(Member member);
    */

    // 단순 저장으로 반환 없음
    void saveMember(Member member);

    void updateMember(Member member);
}