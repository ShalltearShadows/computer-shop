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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        List<RoleVO> roleVOS = roleService.getAll();
        for (RoleVO roleVO : roleVOS) {
            int[] ids = roleService.getPermsByRoleId(roleVO.getId());
            roleVO.setPerm(ids);
        }

        permissionService.getRoleAndPerm(roleVOS);

        return Result.success(roleVOS);
    }

    @PostMapping("/delperm")
    @RequiresPermissions("perm:role:delperm")
    public Result deltePerm(@RequestBody DeletePermVO delete){
        Result role = role();
        List<RoleVO> data = (List<RoleVO>) role.getData();
        RoleVO dto = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId()==delete.getRoleId()) {
                dto = data.get(i);
                break;
            }
        }

        int flag = roleService.deletePerms(delete.getRoleId(), delete.getPermId(), dto.getChildren());

        return flag==1?Result.success(dto.getChildren()):Result.fail("删除失败");
    }


    @PostMapping("/alname")
    public Result alname(@RequestBody AlterRoleNameVO nameDTO){
        int flag = roleService.alterName(nameDTO.getRoleId(), nameDTO.getRoleName());
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }

    @PostMapping("/delrole")
    public Result delete(@RequestBody RoleVO roleVO){
        int flag = roleService.deleteRole(roleVO.getId());
        return flag==1?Result.success("删除成功"):Result.fail("删除失败");
    }


    @GetMapping("/list")
    public Result getAllPerm(){
        List<PermVO> list = permissionService.getOrderPermission();

        return Result.success(list);
    }

    @PostMapping("/assign")
    public Result assign(@RequestBody IdStrVO idStr){
        int flag = roleService.alterPerms(idStr.getRid(), idStr.getIds());
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }

    @GetMapping("/allperms")
    public Result allPerms(){
        List<Permission> all = permissionService.getAll();
        return Result.success(all);
    }

    @PostMapping("/add")
    public Result addRole(@RequestBody Role role){
        int flag = roleService.addRole(role.getName());
        return flag==1?Result.success("添加成功"):Result.fail("添加失败");
    }
}
