/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 19:31
 */
package com.qun.config;

//import org.apache.shiro.mgt.SessionsSecurityManager;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig {

//    @Autowired
//    private JwtFilter jwtFilter;
//
//
//    @Bean
//    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//
//        // inject redisSessionDAO
//        sessionManager.setSessionDAO(redisSessionDAO);
//
//        // other stuff...
//
//        return sessionManager;
//    }
//
//    @Bean
//    public SessionsSecurityManager securityManager(AccountRealm accountRealm, SessionManager sessionManager, RedisCacheManager redisCacheManager) {
//
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
//
//        //inject sessionManager
//        securityManager.setSessionManager(sessionManager);
//
//        // inject redisCacheManager
//        securityManager.setCacheManager(redisCacheManager);
//
//        // other stuff...
//
//        return securityManager;
//    }
//
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/**", "jwt");//通过注解方式校验权限chainDefinition.addPathDefinitions(filterMap);
////        filterMap.put("/**", "authc");
//        return chainDefinition;
//    }
//
//    @Bean("shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
//                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
//
//        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
//        shiroFilter.setSecurityManager(securityManager);
//
//        // 配置filter，判断请求是否有jwt
//        Map<String, Filter> filters = new HashMap();
//        filters.put("jwt", jwtFilter);
//        shiroFilter.setFilters(filters);
//
//        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
//        shiroFilter.setFilterChainDefinitionMap(filterMap);
//        return shiroFilter;
//    }

}