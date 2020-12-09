/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:43
 */
package com.qun.service;

import com.qun.entity.dto.RoleDTO;
import com.qun.entity.po.Role;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAll();
    Role getRole(Long id);
    String[] getPerms(Long id);
}
