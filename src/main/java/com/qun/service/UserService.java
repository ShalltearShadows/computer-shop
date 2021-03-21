/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:27
 */
package com.qun.service;

import com.qun.pojo.dto.UserDTO;
import com.qun.pojo.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll(int start,int num,String query);

    int getTotal();

    User get(long id);

    UserDTO getUserDTO(long id);

    int add(UserDTO user);

    int delete(long id);

    int update(User user);

    int updateRole(Long userId,int RoleId);

    boolean checkLogin(long id, String password);

    int updatePassword(long id,String password);

}
