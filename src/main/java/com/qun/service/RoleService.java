/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/6
 * @Time: 14:43
 */
package com.qun.service;

import com.qun.entity.po.Role;

public interface RoleService {
    Role getRole(Long id);
    String[] getPerms(Long id);
}
