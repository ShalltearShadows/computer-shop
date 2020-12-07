/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:52
 */
package com.qun.service;

import com.qun.entity.po.Permission;
import com.qun.entity.vo.Menu;

import java.util.List;

public interface PermissionService {
    List<Permission> getAll();

    List<Permission> getPermission(String[] ids);

    @Deprecated
    List<Menu> getDisorderMenu(String[] ids);

    List<Menu> getOrderMenu(String[] ids);
}