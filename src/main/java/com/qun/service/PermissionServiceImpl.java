/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:54
 */
package com.qun.service;

import com.qun.entity.dto.PermDTO;
import com.qun.entity.dto.RoleDTO;
import com.qun.entity.dao.Permission;
import com.qun.entity.dto.Menu;
import com.qun.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public List<PermDTO> getOrderPermission() {

        List<Permission> all = getAll();

        return structuredPerm(all);
    }

    /**
     * 结构化权限
     */
    private List<PermDTO> structuredPerm(List<Permission> all) {
        List<PermDTO> perm2 = new ArrayList<>();
        List<PermDTO> perm1 = new ArrayList<>();

        for (Permission m : all) {
            if (m.getLevel()==2){
                perm2.add(new PermDTO(m.getPermId(),m.getParentId(),m.getName()));
            }
        }

        for (PermDTO e : perm2) {
            for (Permission n : all) {
                if (n.getParentId()==e.getId()){
                    e.setChildren(new PermDTO(n.getPermId(),n.getParentId(),n.getName()));
                }
            }
        }

        for (Permission m : all) {
            if (m.getLevel()==1){
                perm1.add(new PermDTO(m.getPermId(),m.getParentId(),m.getName()));
            }
        }

        for (PermDTO p2 : perm2) {
            for (PermDTO p1 : perm1) {
                if (p1.getId()==p2.getParentId()){
                    p1.setChildren(p2);
                }
            }
        }
        return perm1;
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
    public void getRoleAndPerm(List<RoleDTO>  roleDTOS) {
        for (RoleDTO dto : roleDTOS) {
            List<Permission> list = permissionMapper.get(dto.getPerm());

            List<PermDTO> permDTOS = structuredPerm(list);
            dto.setChildren(permDTOS);
        }
    }


}
