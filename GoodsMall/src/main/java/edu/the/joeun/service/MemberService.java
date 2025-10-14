package edu.the.joeun.service;

import edu.the.joeun.mapper.MemberMapper;
import edu.the.joeun.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public void insertMember(Member member){
        String 유저작성패스워드 = member.getPassword();
        String 암호화처리패스워드 = bCryptPasswordEncoder.encode(유저작성패스워드);

        member.setPassword(암호화처리패스워드);

        memberMapper.insertMember(member);
    }

}
