/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.entity.dto.UserDTO;
import com.qun.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAll(@Param("start") int start,@Param("num") int num,@Param("query") String query);

    int getTotal();

    User get(@Param("id") long id);

    int add(UserDTO user);

    int delete(long id);

    int update(User user);

    int updateRole(@Param("userId") Long userId,@Param("roleId") int RoleId);

    User checkLogin(@Param("id") long id, @Param("password") String password);
}
