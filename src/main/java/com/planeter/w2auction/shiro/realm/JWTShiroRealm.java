package com.planeter.w2auction.shiro.realm;

import com.planeter.w2auction.common.utils.JwtUtils;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.UserService;
import com.planeter.w2auction.shiro.matcher.JWTCredentialsMatcher;
import com.planeter.w2auction.shiro.JWTToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
/**
 * 自定义身份认证
 * 基于HMAC（ 散列消息认证码）的控制域
 */
public class JWTShiroRealm extends AuthorizingRealm {
    @Resource
    UserService userInfoService;
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
     * 和controller登录一样，获取用户的salt值，由
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authToken;
        String token = jwtToken.getToken();

        User user = userInfoService.getJwtUser(JwtUtils.getUsername(token));
        if(user == null)
            throw new AuthenticationException("token过期，请重新登录");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //principal
                user.getSalt(), //credential
                getName()); // realmName
        return authenticationInfo;
    }
}
