package cn.sai.shiro.service;

import cn.sai.shiro.domain.User;

public interface UserService {
    /**
     * 根据用户名查询用户对象
    */
    public User queryUserByUsername(String username);
}
