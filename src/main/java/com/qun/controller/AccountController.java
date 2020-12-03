/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:26
 */
package com.qun.controller;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qun.common.dto.LoginDto;
import com.qun.common.lang.Result;
import com.qun.pojo.po.User;

import com.qun.service.UserServiceImpl;

import com.qun.util.JwtUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.get(loginDto.getId());
        Assert.notNull(user,"用户不存在");

        if (!user.getPassword().equals(loginDto.getPassword())){
            return Result.fail("密码不正确");
        }


        String jwt = jwtUtils.generateToken(loginDto.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-Control-Expose-Headers","Authorization");


        return Result.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("address",user.getAddress())
                .map()
        );
    }

    @GetMapping("/logout")
//    @RequiresAuthentication
    public Result logout(){
//        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}