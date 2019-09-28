package com.sy.service.impl;

import com.sy.mapper.UserMapper;
import com.sy.pojo.User;
import com.sy.service.UserService;
import com.sy.uitls.EmailUtils;
import com.sy.uitls.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ssssyy
 * 2019/9/26 11:00
 */
@Service
public class UserServiceImpl implements UserService {
//    @Autowired  类型
//    @Resource  名字
    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(User user) {
        user.setPassword(Md5Utils.md5(user.getPassword()));
        userMapper.add(user);
        EmailUtils.sendEmail(user);
    }

    @Override
    public User checkUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User login(String username, String password) {
//      因为数据库存储的是加密后的数据 所以对密码加密后再和数据库中的数据进行比较
        password=Md5Utils.md5(password);
        return userMapper.findByUserNameAndPassword(username, password);
    }
}
