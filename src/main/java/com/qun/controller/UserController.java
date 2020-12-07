/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/2
 * @Time: 13:57
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.entity.po.User;
import com.qun.entity.vo.Menu;
import com.qun.service.PermissionService;
import com.qun.service.RoleService;
import com.qun.service.UserService;
import com.qun.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @RequiresAuthentication //访问此方法必须先认证
    @GetMapping("/menu")
    public Result menu(){
        Long id = ShiroUtil.getProfile().getId();

        List<Menu> menu = permissionService.getOrderMenu(roleService.getPerms(id));

        return Result.success(menu);
    }

    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public Result list(){
        List<User> all = userService.getAll();

        return Result.success(all);
    }


    @GetMapping("/perm")
    //@RequiresRoles("admin") //访问此方法必须拥有的角色
    @RequiresPermissions("不可能会有的权限") //访问此方法必须具备的权限
    public Result perm(){
        System.err.println("你是超级管理员吗，不然的话，你是不可能会有这个方法的权限的");
        return Result.success("你是超级管理员吗，不然的话，你是不可能会有这个方法的权限的");
    }


    @GetMapping("/test")
    @RequiresPermissions("sys:list:info")
    public Result home(){
        return Result.fail("");
    }

}
