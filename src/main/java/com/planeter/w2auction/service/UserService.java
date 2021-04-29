package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.User;

import java.util.List;

public interface UserService {
    /** 检查username是否已存在 */
    public boolean isValid(String username);
    /** 注册用户 */
    public User register(String username,String password);
    /** 删除用户 */
    void delete(String username);

    /** 更新用户 */
    public void save(User user);
    /** 通过username查找用户信息 */
    public User findByUsername(String username);
    /** 根据用户名+时间戳+盐生成JWTToken */
    public String generateJwtToken(String username);
    /** 获取含JWT加密盐的用户信息 */
    public User getJwtUserInfo(String username);
}
