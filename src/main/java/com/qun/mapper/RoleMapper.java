/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    List<Role> getAll();
    Role getRole(Long id);
    String getPerms(Long id);
    int add(Role role);
    int delete(Long id);
    int update(Role role);
}
