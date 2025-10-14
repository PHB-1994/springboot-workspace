package edu.the.joeun.mapper;

import edu.the.joeun.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(Member member);

}
