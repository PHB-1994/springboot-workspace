package edu.the.joeun.service;

import edu.the.joeun.mapper.UsersMapper;
import edu.the.joeun.model.Users;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private UsersMapper userMapper;

    public List<Users> getAllUsers(){
        return userMapper.getAllUser();
    }

    public void insertUsers(Users user){
        userMapper.insertUsers(user);
    }

}
