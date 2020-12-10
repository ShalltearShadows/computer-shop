/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/10
 * @Time: 10:31
 */
package com.qun.controller;


import com.qun.common.lang.Result;
import com.qun.entity.dto.PermDTO;
import com.qun.entity.dto.RoleDTO;
import com.qun.service.PermissionService;
import com.qun.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perm")
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/role")
    @RequiresPermissions("perm")
    public Result role(){

        List<RoleDTO> roleDTOS = roleService.getAll();
        for (RoleDTO roleDTO : roleDTOS) {
            int[] ids = roleService.getPermsByRoleId(roleDTO.getId());
            roleDTO.setPerm(ids);
        }

        permissionService.getRoleAndPerm(roleDTOS);

        return Result.success(roleDTOS);
    }
}
