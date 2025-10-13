package edu.the.joeun.service;

import edu.the.joeun.mapper.UsersMapper;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private UsersMapper userMapper;

    public List<User> getAllUsers(){
        return userMapper.getAllUser();
    }

    public void insertUser(User user){
        userMapper.insertUser(user);
    }

}
