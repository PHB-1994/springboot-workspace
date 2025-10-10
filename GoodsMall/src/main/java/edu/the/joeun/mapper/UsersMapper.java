package edu.the.joeun.mapper;

import edu.the.joeun.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {

    List<Users> getAllUser();

    void insertUsers(Users users);
}
