package edu.the.joeun.service;

import edu.the.joeun.mapper.UsersMapper;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersMapper userMapper;

    public List<User> getAllUsers(){
        return userMapper.getAllUser();
    }

    public void insertUser(User user){
        /**
         * 비밀번호 암호화 같은 복합 작업 진행하는 공간
         * 프로필 사진을 폴더에 저장하고 폴더 경로도 DB 에 저장 가능
         */
        userMapper.insertUser();
    }
}
