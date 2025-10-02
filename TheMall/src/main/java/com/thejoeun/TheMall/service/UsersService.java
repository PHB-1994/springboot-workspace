package com.thejoeun.TheMall.service;

import com.thejoeun.TheMall.mapper.UsersMapper;
import com.thejoeun.TheMall.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    public List<Users> getAllUsers(){
        return usersMapper.getAllUsers();
    }

    public void insertUsers(Users users){
        usersMapper.insertUsers(users);
    }
}
