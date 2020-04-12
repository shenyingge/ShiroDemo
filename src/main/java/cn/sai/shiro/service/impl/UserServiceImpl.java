package cn.sai.shiro.service.impl;

import cn.sai.shiro.domain.User;
import cn.sai.shiro.service.UserService;

import java.util.Date;

public class UserServiceImpl implements UserService {
    @Override
    public User queryUserByUsername(String username) {
        User user = null;
        switch (username){
            case "zhangsan":
                user = new User(1,"zhangsan","123",new Date());
                break;
        }
        return user;
    }
}
