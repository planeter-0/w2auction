package com.planeter.w2auction.shiro.filter;

import com.planeter.w2auction.common.utils.JwtUtils;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.UserService;
import com.planeter.w2auction.shiro.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtAuthFilter extends AuthenticatingFilter {
    @Resource
    UserService userInfoService;
    // token更新时间.单位秒
    private static final int tokenRefreshInterval = 3000;

    /**
     * 父第一个被调用的方法
     * 返回true则继续，返回false则会调用onAccessDenied()。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response))
            return true; // 不拦截登录请求
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response); //处理登录
        } catch (IllegalStateException e) { // not found any token
            log.error("Not found any token");
        } catch (Exception e) {
            log.error("Error occurs when login", e);
        }
        //不通过调用了isPermissive()方法,检查是否是宽容的url过滤器链映射
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //从header获取token value
        HttpServletRequest httpRequest = WebUtils.toHttp(servletRequest);
        String header = httpRequest.getHeader("x-auth-token");
        String jwtToken = StringUtils.removeStart(header, "Bearer ");
        // 非空且不过期,返回原token
        if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken))
            return new JWTToken(jwtToken);
        return null;//进入isAccessAllowed（）的异常处理逻辑
    }

    /**
     * isAccessAllowed()返回false,返回错误响应
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION);
        //Cors头
        fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        return false;
    }
    /**
     * 认证成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            User user = (User) subject.getPrincipal();
            //检查token过期,若过期生成新token
            Date date = JwtUtils.getIssuedAt(jwtToken.getToken());
            assert date != null;
            if (shouldTokenRefresh(date)) {
                newToken = userInfoService.generateJwtToken(user.getUsername());
            }
        }
        // token被加入响应头
        if (StringUtils.isNotBlank(newToken))
            httpResponse.setHeader("x-auth-token", newToken);
        return true;
    }

    /**
     * 认证失败，回调
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }

    /**
     * 比较 jwt生成时间 是否 晚于 (现在时间 - jwt有效时间 )
     */
    protected boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }
}
