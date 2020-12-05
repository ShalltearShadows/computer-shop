/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 19:41
 */
package com.qun.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.qun.pojo.po.User;
import com.qun.service.UserService;
import com.qun.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AccountRealm extends AuthorizingRealm {
    // JwtUtils使用@Component注解方式注入（需要配置如下代码）
    private static AccountRealm obj;

    @PostConstruct
    public void initial(){
        obj = this;
        obj.jwtUtils = this.jwtUtils;
    }

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    public void setName(){
        setName("AccountRealm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {

        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        AccountProfile profile = (AccountProfile) principalCollection.getPrimaryPrincipal();

        // 1.
//        List<String> perms = new ArrayList<>();
//        List<String> roles = new ArrayList<>();
//
//        perms.add("权限1");
//        roles.add("角色1");

        // 2.
        Set<String> roles = new HashSet<>();
        Set<String> perms = new HashSet<>();
        perms.add("权限1");

//        for (Role role : profile.getRoles()){
//            roles.add(role.getName());
//            for (Permission perm :role.getPermssion()){
//                perms.add(perm.getCode());
//            }
//        }



        //构造返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置权限和角色集合
        info.addStringPermissions(perms);
        info.addRoles(roles);

        return info;
    }

    /**
     * 认证，保存安全数据
     * 目的：比较用户名与密码是否与数据库中一致，将安全数据存入到shiro中保管 **在这里只是检验用户是否存在，因为密码不能放进token**
     * 成功：存入数据； 失败：返回null/抛出异常
     * @param token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) token;


        String id = obj.jwtUtils.getClaimsByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.get(Long.parseLong(id));

        if (user == null){
            throw new UnknownAccountException("账户不存在！");
        }

        if (user.getStatus() == 0){
            throw new LockedAccountException("用户已锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user,profile);

        //profile 为安全数据，getCredentials：获取token
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
