package com.planeter.w2auction.shiro.realm;

import com.planeter.w2auction.entity.*;
import com.planeter.w2auction.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 登录密码认证用的realm
 */
public class DbShiroRealm extends AuthorizingRealm {
    @Resource
    UserInfoService userInfoService;
    @Autowired
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
    //设置凭证匹配器
    public DbShiroRealm() {
        this.setCredentialsMatcher(getHashedCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证
     *
     * @param authenticationToken 进行认证的令牌
     * @return 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();//UsernameAndPasswordToken
        // 从数据库中查找UserInfo信息
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (null == userInfo)
            throw new AuthenticationException("用户不存在");
        //认证成功,返回认证info
        return new SimpleAuthenticationInfo(
                userInfo, //主体
                userInfo.getPassword(), //hashedCredential
                ByteSource.Util.bytes(userInfo.getSalt()), //salt
                getName() // realmName```
        );
    }

    /**
     * 授权
     *
     * @param principalCollection 主体
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();//主体
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //授予角色
        for (SysRole role : userInfo.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getName());
        }
        //授予权限
        simpleAuthorizationInfo.addStringPermissions(userInfoService.getPermissionsByUsername(userInfo.getUsername()));
        return simpleAuthorizationInfo;
    }
}
