/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:45
 */
package com.qun.service;

import com.qun.entity.dto.UserDTO;
import com.qun.mapper.UserMapper;
import com.qun.entity.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll(int start,int num,String query) {
        return userMapper.getAll(start,num,query);
    }

    @Override
    public int getTotal() {
        return userMapper.getTotal();
    }

    @Override
    public User get(long id) {
        return userMapper.get(id);
    }

    @Override
    public UserDTO getUserDTO(long id) {
        User user = userMapper.get(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername()).setMobile(user.getMobile())
                .setAddress(user.getAddress()).setId(user.getId());
        return userDTO;
    }

    @Override
    public int add(UserDTO user) {
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
    public int updateRole(Long userId, int RoleId) {
        return userMapper.updateRole(userId,RoleId);
    }

    @Override
    public boolean checkLogin(long id, String password) {
        return userMapper.checkLogin(id, password) != null;
    }

    @Override
    public int updatePassword(long id, String password) {
        return userMapper.updatePassword(id,password);
    }


}
