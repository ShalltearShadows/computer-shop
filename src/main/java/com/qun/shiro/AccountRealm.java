///**
// * Created by IntelliJ IDEA.
// *
// * @Author: LiQun
// * @Date: 2020/12/1
// * @Time: 19:41
// */
//package com.qun.shiro;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.qun.pojo.po.User;
//import com.qun.service.UserService;
//import com.qun.util.JwtUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
////@Component
//public class AccountRealm extends AuthorizingRealm {
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//
//        return token instanceof JwtToken;
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//
//        JwtToken jwtToken = (JwtToken) token;
//
//        String id = jwtUtils.getClaimsByToken((String) jwtToken.getPrincipal()).getSubject();
//
//        User user = userService.get(Long.parseLong(id));
//
//        if (user == null){
//            throw new UnknownAccountException("账户不存在！");
//        }
//
//        if (user.getStatus() == 0){
//            throw new LockedAccountException("用户已锁定");
//        }
//
//        AccountProfile profile = new AccountProfile();
//        BeanUtil.copyProperties(user,profile);
//
//        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
//    }
//}
