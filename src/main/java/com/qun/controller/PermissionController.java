/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/10
 * @Time: 10:31
 */
package com.qun.controller;


import com.qun.common.lang.Result;
import com.qun.pojo.entity.Permission;
import com.qun.pojo.entity.Role;
import com.qun.pojo.vo.*;
import com.qun.service.PermissionService;
import com.qun.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perm")
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/role")
    @RequiresPermissions("perm:role")
    public Result role(){
        // 获取未设置权限的角色
        List<RoleVO> roleVOS = roleService.getAll();
        // 给角色设置权限
        permissionService.getRoleAndPerm(roleVOS);

        return Result.success(roleVOS);
    }

    @PostMapping("/delperm")
    @RequiresPermissions("perm:role:delperm")
    public Result deltePerm(@RequestBody DeletePermVO delete){

        if (delete.getRoleId()==1){
            return Result.fail("无权删除超级管理员权限");
        }

        int p = delete.getPermId();
        switch (p){
            case 5:
            case 29:
            case 30:
            case 31:
            case 32: return Result.fail("个人基本权限无法删除");
        }

        Result role = role();
        List<RoleVO> data = (List<RoleVO>) role.getData();
        Optional<RoleVO> first = data.stream().filter(roleVO -> roleVO.getId() == delete.getRoleId()).findFirst();

        first.ifPresent(roleVO -> roleService.deletePerms(delete.getRoleId(), delete.getPermId(), roleVO.getPermission()));

        List<RoleVO> list = (List<RoleVO>) role().getData();
        Optional<RoleVO> second = list.stream().filter(roleVO -> roleVO.getId() == delete.getRoleId()).findFirst();

        return second.map(roleVO -> Result.success(roleVO.getPermission())).orElseGet(Result::success);

    }


    @PostMapping("/alname")
    @RequiresPermissions("perm:role:edit")
    public Result alname(@RequestBody AlterRoleNameVO nameDTO){
        if (nameDTO.getRoleId()==1){
            return Result.fail("无权修改超级管理员");
        }
        roleService.alterName(nameDTO.getRoleId(), nameDTO.getRoleName());
        return Result.success();
    }

    @PostMapping("/delrole")
    @RequiresPermissions("perm:role:delete")
    public Result delete(@RequestBody RoleVO roleVO){
        if (roleVO.getId()==1){
            return Result.fail("无权删除超级管理员");
        }
        roleService.deleteRole(roleVO.getId());
        return Result.success();
    }


    @GetMapping("/list")
    public Result getAllPerm(){
        List<PermVO> list = permissionService.getOrderPermission();
        return Result.success(list);
    }

    @PostMapping("/assign")
    @RequiresPermissions("perm:role:assign")
    public Result assign(@RequestBody IdStrVO idStr){
        if (idStr.getRid()==1){
            return Result.fail("无权修改超级管理员权限");
        }
        if (StringUtils.hasLength(idStr.getIds())){
            roleService.alterPerms(idStr.getRid(), idStr.getIds()+",5,29,30,31,32");
        }else {
            roleService.alterPerms(idStr.getRid(), "5,29,30,31,32");
        }
        return Result.success();
    }

    @GetMapping("/allperms")
    public Result allPerms(){
        List<Permission> all = permissionService.getAll();
        return Result.success(all);
    }

    @PostMapping("/add")
    @RequiresPermissions("perm:role:add")
    public Result addRole(@RequestBody Role role){
        roleService.addRole(role.getName());
        return Result.success();
    }
}
