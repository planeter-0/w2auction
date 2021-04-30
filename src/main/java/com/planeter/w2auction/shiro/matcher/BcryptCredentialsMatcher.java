package com.planeter.w2auction.shiro.matcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.planeter.w2auction.entity.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;

import java.io.UnsupportedEncodingException;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/4/30 11:01
 * @status dev
 */
public class BcryptCredentialsMatcher  implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //要验证的明文密码
        String plaintext = new String(userToken.getPassword());
        //数据库中的加密后的密文
        String hashed = info.getCredentials().toString();
        return BCrypt.checkpw(plaintext, hashed);
    }
}
