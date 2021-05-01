package com.planeter.w2auction.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.planeter.w2auction.dao.UserDao;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Planeter
 * @description: JWT工具类
 * @date 2021/4/29 20:48
 * @status dev
 */
public class JwtUtils {


    /**
     * @return token的签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuedAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,expireTime后过期
     *
     * @param username 用户名
     * @param time     过期时间(秒)
     * @return 加密token
     */
    public static String sign(String username, String salt, long time) {
        try {
            Date date = new Date(System.currentTimeMillis() + time * 1000);
            Algorithm algorithm = Algorithm.HMAC256(salt);
            // username+date
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)//过期时间
                    .withIssuedAt(new Date())//签发时间
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @return token是否过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Calendar.getInstance().getTime();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 生成随机盐,长度32位
     */
    public static String generateSalt() {
        return new SecureRandomNumberGenerator().nextBytes(16).toHex();
    }

}
