/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:54
 */
package com.qun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.qun.pojo.vo.PermVO;
import com.qun.pojo.vo.RoleVO;
import com.qun.pojo.entity.Permission;
import com.qun.pojo.vo.Menu;
import com.qun.mapper.PermissionMapper;
import com.qun.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> getAll() {
        return permissionMapper.getAll();
    }

    @Override
    public List<PermVO> getOrderPermission() {

        List<Permission> all = getAll();
        List<PermVO> permVOS = structuredPerm(all);
        permVOS.remove(4);
        return permVOS;
    }

    /**
     * 结构化权限
     */
    private List<PermVO> structuredPerm(List<Permission> all) {
        Map<Integer, List<PermVO>> collect = all.stream().map(permission -> {
            PermVO vo = new PermVO();
            BeanUtil.copyProperties(permission,vo);
            return vo;
        }).collect(Collectors.groupingBy(PermVO::getLevel));

        if (collect.get(3)!=null) {
            collect.get(2).stream().peek(perm -> {
                for (PermVO p3 : collect.get(3)) {
                    if (p3.getParentId() == perm.getPermId()) {
                        perm.setChildren(p3);
                    }
                }
            }).collect(Collectors.toList());
        }

        collect.get(1).stream().peek(perm -> {
            for (PermVO p : collect.get(2)) {
                if (p.getParentId() == perm.getPermId()) {
                    perm.setChildren(p);
                }
            }
        }).collect(Collectors.toList());

        return collect.get(1);
    }

    @Override
    public List<Permission> getPermission(int[] ids){
        return permissionMapper.get(ids);
    }

    @Override
    public List<Menu> getDisorderMenu(int[] ids) {
        List<Permission> list = getPermission(ids);
        List<Permission> all = getAll();

        for (int i = 0; i < all.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                //找到了一条应该拥有的菜单
                if (all.get(i).getPerm().startsWith(list.get(j).getPerm())) {
                    if (all.get(i).getLevel()==3){
                        all.remove(i--);
                    }
                    break;
                }else if (j == list.size()-1){
                    all.remove(i--);
                }
            }
        }

        List<Menu> menus = new ArrayList<>();

        for (Permission permission : all) {
            Menu m = new Menu(permission.getPermId(),permission.getName(),permission.getLevel(),permission.getParentId());
            if (m.getLevel()==2){
                m.setPath(permission.getPerm().replace(":","/"));
            }
            menus.add(m);
        }

        return menus;
    }

    public List<Menu> getOrderMenu(int[] ids){
        List<Menu> menu = new ArrayList<>();

        List<Menu> all = getDisorderMenu(ids);

        for (Menu m : all) {
            if (m.getLevel()==1){
                menu.add(m);
            }
        }

        for (Menu e : menu) {
            for (Menu n : all) {
                if (n.getParentId()==e.getId()){
                    e.setChildren(n);
                }
            }
        }

        return menu;
    }

    /**
     * 获取每个角色结构化的全部权限
     */
    @Override
    public void getRoleAndPerm(List<RoleVO> roleVOS) {
        for (RoleVO role : roleVOS) {
            List<Permission> list = permissionMapper.get(role.getPerm());
            role.setPermission(structuredPerm(list));
        }
    }


}
