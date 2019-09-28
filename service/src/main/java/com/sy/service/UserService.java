package com.sy.service;

import com.sy.pojo.User;

/**
 * ssssyy
 * 2019/9/26 10:59
 */
public interface UserService {
    //注册
    void register(User user);
    //检查用户名是否存在
    User checkUserName(String username);
    //登录
    User login(String username,String password);
}
