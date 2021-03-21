/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    List<Role> getAll();

    Role getRole(Long id);

    String getPerms(Long id);

    String getPermsByRoleId(int id);

    int add(Role role);

    int delete(int id);

    int deletePerms(@Param("id") int id,@Param("perm") String perm);

    int alterName(@Param("rid") int rid,@Param("name") String name);

    int alterPerms(@Param("rid") int rid,@Param("ids") String ids);

    int addRole(String name);
}
