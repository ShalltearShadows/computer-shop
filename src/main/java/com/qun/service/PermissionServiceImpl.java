/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:54
 */
package com.qun.service;

import com.qun.entity.po.Permission;
import com.qun.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> getAll() {
        return permissionMapper.getAll();
    }

    @Override
    public List<Permission> getPermission(String[] ids){
        return permissionMapper.get(ids);
    }
}
