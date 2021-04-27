package com.planeter.w2auction.shiro.realm;

import com.planeter.w2auction.entity.*;
import com.planeter.w2auction.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * 登录密码认证用的realm
 */
public class DbShiroRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    //设置凭证匹配器
    public DbShiroRealm() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        this.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();//UsernameAndPasswordToken
        // 从数据库中查找UserInfo信息
        User user = userService.findByUsername(username);
        if (null == user)
            throw new AuthenticationException("用户不存在");
        //认证成功,返回认证info
        return new SimpleAuthenticationInfo(
                user, //主体
                user.getPassword(), //hashedCredential
                ByteSource.Util.bytes(user.getSalt()), //salt
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
        User userInfo = (User) principalCollection.getPrimaryPrincipal();//主体
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //授予角色
        for (Role role : userInfo.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getName());
        }
        //授予权限
        simpleAuthorizationInfo.addStringPermissions(userService.getPermissionsByUsername(userInfo.getUsername()));
        return simpleAuthorizationInfo;
    }
}
