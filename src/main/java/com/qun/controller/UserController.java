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
import com.qun.util.ShiroUtil;
//import org.apache.shiro.authz.annotation.RequiresAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;



//    @RequiresAuthentication
    @GetMapping("/menu")
    public Result menu(){

//        Long id = ShiroUtil.getProfile().getId();

//        System.out.println(id);

        log.info("===========================================");

        return Result.success(userService.get(1700701L));
    }


}
