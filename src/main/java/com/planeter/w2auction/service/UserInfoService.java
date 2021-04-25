package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.Member;
import com.planeter.w2auction.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    /** 通过username查找用户信息 */
    public UserInfo findByUsername(String username);
    /** 检查username是否已存在
     * @return*/
    public boolean isValid(String username);
    /** 注册用户 */
    public UserInfo createUserInfo(UserInfo userInfo);
    /** 获取角色 */
    public List<String> getRolesByUsername(String username);
    /** 获取权限 */
    public List<String> getPermissionsByUsername(String username);
    /** 删除用户 */
    public void deleteUserInfo(Integer id);
    /** 根据用户信息生成JWTToken */
    public String generateJwtToken(String username);
    /** 获取含JWT加密盐的用户信息 */
    public UserInfo getJwtUserInfo(String username);
}
