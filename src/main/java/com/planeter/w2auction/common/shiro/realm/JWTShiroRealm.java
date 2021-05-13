package com.planeter.w2auction.common.shiro.realm;

import com.planeter.w2auction.common.utils.JwtUtils;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.UserService;
import com.planeter.w2auction.common.shiro.matcher.JWTCredentialsMatcher;
import com.planeter.w2auction.common.shiro.JWTToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
public class JWTShiroRealm extends AuthorizingRealm {
    @Resource
    UserService userService;
    // 设置Matcher
    public JWTShiroRealm(){
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }
    /**
     * 设置支持的token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
    /**
     * JWTRealm只负责登陆后的请求认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 获取用户的salt值
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authToken;
        String token = jwtToken.getToken();
        User user = userService.getJwtUser(JwtUtils.getUsername(token));
        if(user == null)
            throw new AuthenticationException("token过期，请重新登录");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //principal
                user.getSalt(), //credential
                getName()); // realmName
        return authenticationInfo;
    }
}
