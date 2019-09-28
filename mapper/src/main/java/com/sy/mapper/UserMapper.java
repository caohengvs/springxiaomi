package com.sy.mapper;

import com.sy.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ssssyy
 * 2019/9/26 10:10
 */
public interface UserMapper {
    List<User> findAll();
    User findById(Integer id);
    User findByUserNameAndPassword(@Param("username") String username,@Param("passsword") String password);
    User findByUserName(String username);
    void add(User user);
    void update(User user);
    void delete(Integer id);
}
