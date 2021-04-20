/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.pojo.vo.UserVO;
import com.qun.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAll(@Param("start") int start,@Param("num") int num,@Param("query") String query);

    int getTotal(@Param("start") int start,@Param("num") int num,@Param("query") String query);

    User get(@Param("id") long id);

    int add(UserVO user);

    int delete(long id);

    int update(User user);

    int updateRole(@Param("userId") Long userId,@Param("roleId") int RoleId);

    User checkLogin(@Param("id") long id, @Param("password") String password);

    int updatePassword(@Param("id") long id,@Param("password") String password);
}
