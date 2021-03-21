/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:52
 */
package com.qun.service;

import com.qun.pojo.dto.PermDTO;
import com.qun.pojo.dto.RoleDTO;
import com.qun.pojo.entity.Permission;
import com.qun.pojo.dto.Menu;

import java.util.List;

public interface PermissionService {
    List<Permission> getAll();

    List<PermDTO> getOrderPermission();

    List<Permission> getPermission(int[] ids);

    @Deprecated
    List<Menu> getDisorderMenu(int[] ids);

    List<Menu> getOrderMenu(int[] ids);

    void getRoleAndPerm(List<RoleDTO> roleDTOS);

}
