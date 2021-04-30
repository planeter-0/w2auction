package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.User;


public interface UserService {
    /** 检查username是否已存在 */
    boolean isValid(String username);
    /** 注册用户 */
    User register(String username,String password);
    /** 删除用户 */
    void delete(String username);
    /** 更新用户 */
    void save(User user);
    /** 通过username查找用户信息 */
    User findByUsername(String username);
    /** 根据用户名+时间戳+盐生成JWTToken */
    String generateJwtToken(String username);
    /** 获取含JWT加密盐的用户信息 */
    User getJwtUser(String username);
    /** 是否是管理员用户 */
    boolean isAdminUser(String username);
}
