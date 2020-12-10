/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:45
 */
package com.qun.service;

import cn.hutool.core.bean.BeanUtil;
import com.qun.entity.dto.RoleDTO;
import com.qun.entity.po.Permission;
import com.qun.entity.po.Role;
import com.qun.mapper.PermissionMapper;
import com.qun.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDTO> getAll() {
        List<Role> all = roleMapper.getAll();
        List<RoleDTO> roles = new ArrayList<>(all.size());
        for (int i = 0; i < all.size(); i++) {
            roles.add(new RoleDTO());
            BeanUtil.copyProperties(all.get(i),roles.get(i));
        }

        return roles;
    }

    @Override
    public Role getRole(Long id) {
        return roleMapper.getRole(id);
    }

    @Override
    public int[] getPerms(Long id) {
        String[] split = roleMapper.getPerms(id).split(",");
        int[] perms = new int[split.length];
        for (int i = 0; i < perms.length; i++) {
            perms[i] = Integer.parseInt(split[i]);
        }

        return perms;
    }

    public int[] getPermsByRoleId(int id){

        String[] split = roleMapper.getPermsByRoleId(id).split(",");
        int[] perms = new int[split.length];
        for (int i = 0; i < perms.length; i++) {
            perms[i] = Integer.parseInt(split[i]);
        }

        return perms;
    }

}
