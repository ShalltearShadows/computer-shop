/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.pojo.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAll();
    User get(@Param("id") long id);
    int add(User user);
    int delete(long id);
    int update(User user);
    User checkLogin(@Param("id") long id, @Param("password") String password);
}
