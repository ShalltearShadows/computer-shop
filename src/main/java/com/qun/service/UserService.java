/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:27
 */
package com.qun.service;

import com.qun.common.lang.Result;
import com.qun.pojo.vo.LoginVO;
import com.qun.pojo.vo.UserVO;
import com.qun.pojo.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    List<User> getAll(int start,int num,String query);

    int getTotal();

    User get(long id);

    UserVO getUserVO(long id);

    int add(UserVO user);

    int delete(long id);

    int update(User user);

    int updateRole(Long userId,int RoleId);

    boolean checkLogin(long id, String password);

    int updatePassword(long id,String password);

    Result login(LoginVO loginVO, HttpServletResponse response);

    void upload(MultipartFile file);

    Result avatar();

    Result register(UserVO user, HttpServletResponse response);
}
