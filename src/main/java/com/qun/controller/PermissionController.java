/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/10
 * @Time: 10:31
 */
package com.qun.controller;


import com.qun.common.lang.Result;
import com.qun.entity.dto.*;
import com.qun.entity.po.Permission;
import com.qun.entity.po.Role;
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

        List<RoleDTO> roleDTOS = roleService.getAll();
        for (RoleDTO roleDTO : roleDTOS) {
            int[] ids = roleService.getPermsByRoleId(roleDTO.getId());
            roleDTO.setPerm(ids);
        }

        permissionService.getRoleAndPerm(roleDTOS);

        return Result.success(roleDTOS);
    }

    @PostMapping("/delperm")
    @RequiresPermissions("perm:role:delperm")
    public Result deltePerm(@RequestBody DeletePermDTO delete){
        Result role = role();
        List<RoleDTO> data = (List<RoleDTO>) role.getData();
        RoleDTO dto = null;
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
    public Result alname(@RequestBody AlterRoleNameDTO nameDTO){
        int flag = roleService.alterName(nameDTO.getRoleId(), nameDTO.getRoleName());
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }

    @PostMapping("/delrole")
    public Result delete(@RequestBody RoleDTO roleDTO){
        int flag = roleService.deleteRole(roleDTO.getId());
        return flag==1?Result.success("删除成功"):Result.fail("删除失败");
    }


    @GetMapping("/list")
    public Result getAllPerm(){
        List<PermDTO> list = permissionService.getOrderPermission();

        return Result.success(list);
    }

    @PostMapping("/assign")
    public Result assign(@RequestBody IdStrDTO idStr){
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
