/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/2
 * @Time: 13:57
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.pojo.vo.*;
import com.qun.pojo.entity.User;
import com.qun.service.PermissionService;
import com.qun.service.RoleService;
import com.qun.service.UserService;
import com.qun.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
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
    @RequiresPermissions("user:list:query")
    public Result list(@RequestParam("query") String query,@RequestParam("pagesize") int size,@RequestParam("pagenum") int num){
        int start = (num-1)*size;

        List<User> all = userService.getAll(start, size, "".equals(query)?null:query);
        int total = userService.getTotal(start, size, "".equals(query)?null:query);

        return Result.success(new UserListVO().setUsers(all).setTotal(total));
    }

    @PostMapping("/add")
    @RequiresPermissions("user:list:add")
    public Result add(@Validated @RequestBody UserVO user){
        userService.add(user);
        return Result.success();
    }

    /**
     * 获取将要修改的用户的信息
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("user:list:edit")
    public Result getEditInfo(@PathVariable("id") Long id){
        UserVO user = userService.getUserVO(id);
        return Result.success(user);
    }

    @PostMapping("/edit")
    @RequiresPermissions("user:list:edit")
    public Result edit(@RequestBody UserVO userVO){
        User user = new User().setId(userVO.getId()).setAddress(userVO.getAddress())
                .setMobile(userVO.getMobile()).setUsername(userVO.getUsername());
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/ban")
    @RequiresPermissions("user:list:ban")
    public Result ban(@RequestBody User user){
        System.out.println(user);
        User u = userService.get(user.getId());
        if (u.getRole()==1){
            return Result.fail("无权禁止超级管理员");
        }
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions("user:list:delete")
    public Result delete(@NotNull @PathVariable("id") Long id){
        userService.delete(id);
        return Result.success();
    }

    @GetMapping("/info")
    @RequiresAuthentication
    public Result info(){
        Long id = ShiroUtil.getProfile().getId();
        return Result.success(userService.get(id).setPassword(""));
    }

    @PostMapping("/alter")
    public Result alter(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/password")
    public Result password(@RequestBody PwdVO pwd){
        userService.updatePassword(pwd.getId(),pwd.getPassword());
        return Result.success();
    }


//    @GetMapping("/perm")
//    @RequiresRoles("admin") //访问此方法必须拥有的角色
//    @RequiresPermissions("不可能会有的权限") //访问此方法必须具备的权限
//    public Result perm(){
//        System.err.println("你是超级管理员吗，不然的话，你是不可能会有这个方法的权限的");
//        return Result.success("你是超级管理员吗，不然的话，你是不可能会有这个方法的权限的");
//    }

    /**
     * 上传头像
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        if(file==null){
            return Result.fail("图片为空");
        }
        userService.upload(file);
        return avatar();
    }

    @GetMapping("/avatar")
    public Result avatar() {
        return userService.avatar();
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserVO user, HttpServletResponse response){
        return userService.register(user,response);
    }

}
