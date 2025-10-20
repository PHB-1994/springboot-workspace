package edu.thejoeun.member.model.mapper;

import edu.thejoeun.member.model.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member getMemberByEmail(String memberEmail);
}
