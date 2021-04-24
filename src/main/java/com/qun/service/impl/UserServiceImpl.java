/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 15:45
 */
package com.qun.service.impl;

import cn.hutool.core.map.MapUtil;
import com.qun.common.lang.Result;
import com.qun.pojo.vo.LoginVO;
import com.qun.pojo.vo.UserVO;
import com.qun.mapper.UserMapper;
import com.qun.pojo.entity.User;
import com.qun.service.UserService;
import com.qun.util.ImageUtil;
import com.qun.util.JwtUtils;
import com.qun.util.ShiroUtil;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public List<User> getAll(int start,int num,String query) {
        return userMapper.getAll(start,num,query);
    }

    @Override
    public int getTotal(int start,int num,String query) {
        return userMapper.getTotal(start,num,query);
    }

    @Override
    public User get(long id) {
        return userMapper.get(id);
    }

    @Override
    public UserVO getUserVO(long id) {
        User user = userMapper.get(id);
        UserVO userVO = new UserVO();
        userVO.setUsername(user.getUsername()).setMobile(user.getMobile())
                .setAddress(user.getAddress()).setId(user.getId());
        return userVO;
    }

    @Override
    public int add(UserVO user) {
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

    @Override
    public Result login(LoginVO loginVO, HttpServletResponse response) {

        User user = get(loginVO.getId());
        Assert.notNull(user,"用户不存在");
        Assert.isTrue(user.getPassword().equals(loginVO.getPassword()),"密码不正确");
        Assert.isTrue(user.getStatus(),"该用户已禁用");


        String jwt = jwtUtils.generateToken(loginVO.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-Control-Expose-Headers","Authorization");

        System.out.println(user.getId()+"已登录");

        return Result.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("address",user.getAddress())
                .put("role",user.getRole())
                .map()
        );
    }

    @Override
    public void upload(MultipartFile file) {
        String upload = "D:\\upload\\avatar";
        String name = file.getOriginalFilename();
        assert name != null;
        String suffix = "png";
        String imgName = ShiroUtil.getProfile().getId()+"."+suffix;
        File image = new File(upload,imgName);

        try {
            file.transferTo(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int w = 238;
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bi != null;
        double width = bi.getWidth();
        int h = (int) (bi.getHeight()*(w/width));

        ImageUtil.changeSize(w,h,upload+"//"+imgName);
    }

    @Override
    public Result avatar() {
        long id = ShiroUtil.getProfile().getId();
        File file = new File("D:\\upload\\avatar\\" + id + ".png");
        if (!file.exists()){
            file = new File("D:\\upload\\avatar\\00000.png");
        }

        byte[] data = null;
        try (InputStream is = new FileInputStream(file)){
            data = new byte[is.available()];
            is.read(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
        return Result.success(encoder.encodeToString(data));
    }

    @Override
    public Result register(UserVO user, HttpServletResponse response) {
        add(user);

        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization",jwt);
        response.setHeader("Access-Control-Expose-Headers","Authorization");


        return Result.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("address",user.getAddress())
                .put("role",4)
                .map()
        );
    }


}
