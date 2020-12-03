/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:45
 */
package com.qun.service;

import com.qun.mapper.UserMapper;
import com.qun.pojo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User get(long id) {
        return userMapper.get(id);
    }

    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int delete(long id) {
        return userMapper.delete(id);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public boolean checkLogin(long id, String password) {
        return userMapper.checkLogin(id, password) != null;
    }
}
