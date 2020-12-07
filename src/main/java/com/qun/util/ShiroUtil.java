/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/2
 * @Time: 18:51
 */
package com.qun.util;

import com.qun.entity.po.Permission;
import com.qun.service.PermissionService;
import com.qun.shiro.AccountProfile;
import com.qun.shiro.AccountRealm;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ShiroUtil {


    private static ShiroUtil obj;

    @PostConstruct
    public void initial(){
        obj = this;
        obj.cacheManager = this.cacheManager;
    }

    @Autowired
    @SuppressWarnings("all")
    private RedisCacheManager cacheManager;

    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

    public static void clearUserRedisCache(){
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();

        Cache<Object, Object> cache = obj.cacheManager.getCache(realmName.split("_")[0]+".authorizationCache");

        cache.remove(ShiroUtil.getProfile().getId());

    }
}
