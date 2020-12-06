/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:52
 */
package com.qun.service;

import com.qun.entity.po.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getAll();
    List<Permission> getPermission(String[] ids);
}
