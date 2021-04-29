package com.planeter.w2auction.shiro.matcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.planeter.w2auction.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import java.io.UnsupportedEncodingException;

/**
 * @author Planeter
 * JWT凭证匹配器
 * JWTShiroRealm的匹配器
 */
@Slf4j
public class JWTCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String) authenticationToken.getCredentials();
        Object stored = authenticationInfo.getCredentials();
        String salt = stored.toString();

        User user = (User) authenticationInfo.getPrincipals().getPrimaryPrincipal();
        try {
            // jwt verify
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(salt))
                    .withClaim("username", user.getUsername())
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            log.error("Token Error:{}", e.getMessage());
        }
        return false;
    }
}
