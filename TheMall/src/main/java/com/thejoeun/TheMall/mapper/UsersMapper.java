package com.thejoeun.TheMall.mapper;

import com.thejoeun.TheMall.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {

    List<Users> getAllUsers();
    void insertUsers(Users users);
}
