/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:45
 */
package com.qun.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.druid.util.StringUtils;
import com.qun.entity.dto.PermDTO;
import com.qun.entity.dto.RoleDTO;
import com.qun.entity.dao.Role;
import com.qun.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.shiro.util.StringUtils.split;

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
        String permsByRoleId = roleMapper.getPermsByRoleId(id);
        if (StringUtils.isEmpty(permsByRoleId)){
            return null;
        }
        String[] split = permsByRoleId.split(",");
        int[] perms = new int[split.length];
        for (int i = 0; i < perms.length; i++) {
            perms[i] = Integer.parseInt(split[i]);
        }

        return perms;
    }

    @Override
    public int deletePerms(int rid,int pid,List<PermDTO> permsDTO) {
        if (permsDTO==null){
            permsDTO = new ArrayList<>();
        }
        flag:for (int i = 0; i < permsDTO.size(); i++) {
            if (permsDTO.get(i).getId()==pid){
                permsDTO.remove(i);
                break flag;
            }
            List<PermDTO> children = permsDTO.get(i).getChildren();
            if (children==null){
                continue;
            }
            for (int j = 0; j < children.size(); j++) {
                if (children.get(j).getId()==pid){
                    children.remove(j);
                    break flag;
                }
                List<PermDTO> list = children.get(j).getChildren();
                if (list==null){
                    continue;
                }
                for (int k = 0; k < list.size(); k++) {
                    if (list.get(k).getId()==pid){
                        list.remove(k);
                        break flag;
                    }
                }
            }
        }

        StringBuilder perm = new StringBuilder("");


        for (PermDTO permDTO : permsDTO) {
            perm.append(permDTO.getId()).append(",");
            if (permDTO.getChildren()==null){
                continue;
            }
            for (PermDTO child : permDTO.getChildren()) {
                perm.append(child.getId()).append(",");
                if (child.getChildren()==null){
                    continue;
                }
                for (PermDTO childChild : child.getChildren()) {
                    perm.append(childChild.getId()).append(",");
                }
            }
        }
        if (perm.length()>0){
            perm.deleteCharAt(perm.length()-1);
        }

        return roleMapper.deletePerms(rid,perm.toString());
    }

    @Override
    public int alterName(int rid, String name) {
        return roleMapper.alterName(rid,name);
    }

    @Override
    public int deleteRole(int rid) {
        return roleMapper.delete(rid);
    }

    @Override
    public int alterPerms(int rid, String ids) {
        return roleMapper.alterPerms(rid,ids);
    }

    @Override
    public int addRole(String name) {
        return roleMapper.addRole(name);
    }

}
