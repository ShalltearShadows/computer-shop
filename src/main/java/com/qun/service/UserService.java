/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:27
 */
package com.qun.service;

import com.qun.pojo.po.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(long id);

    int add(User user);

    int delete(long id);

    int update(User user);

    boolean checkLogin(long id, String password);

}
