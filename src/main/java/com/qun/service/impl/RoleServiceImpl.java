/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:45
 */
package com.qun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.qun.mapper.PermissionMapper;
import com.qun.pojo.vo.PermVO;
import com.qun.pojo.vo.RoleVO;
import com.qun.pojo.entity.Role;
import com.qun.mapper.RoleMapper;
import com.qun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleVO> getAll() {
        List<Role> all = roleMapper.getAll();
        List<RoleVO> roles = new ArrayList<>(all.size());
        for (int i = 0; i < all.size(); i++) {
            roles.add(new RoleVO());
            BeanUtil.copyProperties(all.get(i),roles.get(i));
            if (all.get(i).getPerms().length()==0) continue;
            String[] split = all.get(i).getPerms().split(",");

            int[] perms = new int[split.length];

            for (int j = 0; j < perms.length; j++) {
                perms[j] = Integer.parseInt(split[j]);
            }
            roles.get(i).setPerm(perms);
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


    @Override
    public void deletePerms(int rid,int pid,List<PermVO> perms) {
        PermVO root = new PermVO();
        List<Integer> array = new ArrayList<>();
        root.setPermId(-1);
        root.setPerms(perms);
        preorderDelete(root,pid,0,array);
        array.remove(0);
        String collect = array.stream().map(String::valueOf).collect(Collectors.joining(","));
        roleMapper.deletePerms(rid,collect);
    }

    private void preorderDelete(PermVO root,int pid,int yes,List<Integer> array){

        if (root.getPermId()==pid){
            yes = 1;
        }
        if (yes == 0){
            array.add(root.getPermId());
        }
        if (root.getChildren()==null) return;
        for (PermVO child : root.getChildren()) {
            preorderDelete(child,pid,yes,array);
        }
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
