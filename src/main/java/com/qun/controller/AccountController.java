/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:26
 */
package com.qun.controller;

import com.qun.pojo.vo.LoginVO;
import com.qun.common.lang.Result;
import com.qun.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginVO loginVO, HttpServletResponse response) {
        return userService.login(loginVO,response);
    }

    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
