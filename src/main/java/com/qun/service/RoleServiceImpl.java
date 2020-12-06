/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:45
 */
package com.qun.service;

import com.qun.entity.po.Permission;
import com.qun.entity.po.Role;
import com.qun.mapper.PermissionMapper;
import com.qun.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRole(Long id) {
        return roleMapper.getRole(id);
    }

    @Override
    public String[] getPerms(Long id) {
        return roleMapper.getPerms(id).split(",");
    }

}
