package com.planeter.w2auction.common.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;

public class JWTToken implements HostAuthenticationToken {
    private String token;
    private String host;

    public JWTToken(String token) {
        this(token, null);
    }

    public JWTToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    public String getToken() {
        return token;
    }

    public String getHost() {
        return host;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String toString() {
        return token + ':' + host;
    }
}
