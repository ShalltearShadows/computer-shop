/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/2
 * @Time: 13:57
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.service.UserService;
import com.qun.shiro.AccountProfile;
import com.qun.util.ShiroUtil;
//import org.apache.shiro.authz.annotation.RequiresAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequiresAuthentication //访问此方法必须先认证
    @GetMapping("/menu")
    public Result menu(){
        log.info("=====================menu======================");
        Long id = ShiroUtil.getProfile().getId();

        Subject subject = SecurityUtils.getSubject();


        return Result.success(userService.get(1700701L));
    }


    @GetMapping("/home")
    @RequiresRoles("admin") //访问此方法必须拥有的角色
    @RequiresPermissions("acc") //访问此方法必须具备的权限
    public Result a(){
        return Result.success("个人主页");
    }

    @GetMapping("/ww")
    public Result b(){
        return Result.success("ww");
    }

    @GetMapping("/un")
    public Result u(){
        return Result.fail("无权限");
    }

    @GetMapping("/to")
    public Result to(){

        return Result.fail("未登录");
    }

}
