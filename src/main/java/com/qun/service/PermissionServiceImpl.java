/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:54
 */
package com.qun.service;

import com.qun.entity.po.Permission;
import com.qun.entity.vo.Menu;
import com.qun.mapper.PermissionMapper;
import com.qun.util.ShiroUtil;
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
    public List<Permission> getPermission(String[] ids){
        return permissionMapper.get(ids);
    }

    @Override
    public List<Menu> getDisorderMenu(String[] ids) {
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

    public List<Menu> getOrderMenu(String[] ids){
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
}
