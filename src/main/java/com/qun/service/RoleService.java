/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:43
 */
package com.qun.service;

import com.qun.pojo.vo.PermVO;
import com.qun.pojo.vo.RoleVO;
import com.qun.pojo.entity.Role;

import java.util.List;

public interface RoleService {
    List<RoleVO> getAll();

    Role getRole(Long id);

    int[] getPerms(Long id);

    int[] getPermsByRoleId(int id);

    int deletePerms(int rid, int pid, List<PermVO> permsDTO);

    int alterName(int rid,String name);

    int deleteRole(int rid);

    int alterPerms(int rid,String ids);

    int addRole(String name);
}
