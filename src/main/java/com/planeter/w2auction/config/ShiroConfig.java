package com.planeter.w2auction.config;

import com.planeter.w2auction.common.filter.XSSFilter;
import com.planeter.w2auction.service.UserService;
import com.planeter.w2auction.common.filter.CorsFilter;
import com.planeter.w2auction.common.filter.JwtAuthFilter;
import com.planeter.w2auction.common.shiro.realm.DbShiroRealm;
import com.planeter.w2auction.common.shiro.realm.JWTShiroRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager, UserService userService) throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter((Filter) shiroFilter(securityManager, userService).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);

        return filterRegistration;
    }

    /**
     * 双realm, 任意通过一个即认证
     */
    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(), dbShiroRealm()));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

//    /**
//     * 禁用session
//     */
//    @Bean
//    protected SessionStorageEvaluator sessionStorageEvaluator() {
//        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
//        sessionStorageEvaluator.setSessionStorageEnabled(false);
//        return sessionStorageEvaluator;
//    }

    @Bean("dbRealm")
    public Realm dbShiroRealm() {
        return new DbShiroRealm();
    }

    @Bean("jwtRealm")
    public Realm jwtShiroRealm() {
        return new JWTShiroRealm();
    }

    /**
     * 加入自定义jwt过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserService userService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("jwt", new JwtAuthFilter());
        filterMap.put("xss",new XSSFilter());
        filterMap.put("cors",new CorsFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return factoryBean;
    }

    /**
     * url过滤器链,负责authenticate
     * 注解和服务类方法内负责authorize
     */
    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition c = new DefaultShiroFilterChainDefinition();
        //无需认证
        c.addPathDefinition("/register", "anon,xss,cors");
        c.addPathDefinition("/login", "anon,xss,cors");
        c.addPathDefinition("/logout", "anon,xss,cors");
        c.addPathDefinition("/item/**","anon,xss,cors");
        c.addPathDefinition("/searchItem","anon,cors");
        c.addPathDefinition("/image/**","anon,cors");
        //其他路径均需要jwt过滤器通过
        c.addPathDefinition("/**", "noSessionCreation,jwt,xss,cors");
        return c;
    }
    /**
     * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，
     * 导致返回404。加入这项配置能解决这个bug
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }


}
