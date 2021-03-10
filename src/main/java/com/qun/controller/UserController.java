/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/2
 * @Time: 13:57
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.entity.dto.*;
import com.qun.entity.dto.Menu;
import com.qun.entity.dao.User;
import com.qun.service.PermissionService;
import com.qun.service.RoleService;
import com.qun.service.UserService;
import com.qun.util.ImageUtil;
import com.qun.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @RequiresAuthentication //访问此方法必须先认证
    @GetMapping("/menu")
    public Result menu(){
        Long id = ShiroUtil.getProfile().getId();

        List<Menu> menu = permissionService.getOrderMenu(roleService.getPerms(id));

        return Result.success(menu);
    }

    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public Result list(@RequestParam("query") String query,@RequestParam("pagesize") int size,@RequestParam("pagenum") int num){
        int start = (num-1)*size;

        List<User> all = userService.getAll(start, size, "".equals(query)?null:query);
        int total = userService.getTotal();

        return Result.success(new UserListDTO().setUsers(all).setTotal(total));
    }

    @PostMapping("/add")
    @RequiresPermissions("user:list:add")
    public Result add(@Validated @RequestBody UserDTO user){
        int flag = userService.add(user);

        return flag==1?Result.success("添加成功"):Result.fail("添加失败");
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("user:list:edit")
    public Result edit(@PathVariable("id") Long id){
        UserDTO user = userService.getUserDTO(id);
        return Result.success(user);
    }

    @PostMapping("/edit")
    @RequiresPermissions("user:list:edit")
    public Result edit(@RequestBody UserDTO userDTO){
        User user = new User().setId(userDTO.getId()).setAddress(userDTO.getAddress())
                .setMobile(userDTO.getMobile()).setUsername(userDTO.getUsername());
        int flag = userService.update(user);
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions("user:list:delete")
    public Result delete(@NotNull @PathVariable("id") Long id){
        int flag = userService.delete(id);
        return flag==1?Result.success("删除成功"):Result.fail("删除失败");
    }


    @GetMapping("/roles")
    @RequiresPermissions("user:list:assign")
    public Result roles(){
        List<RoleDTO> role = roleService.getAll();
        return Result.success(role);
    }

    @PostMapping("/role/{id}")
    @RequiresPermissions("user:list:role")
    public Result role(@NotNull @PathVariable("id") Long id,@RequestBody RoleDTO roleDTO){
        int flag = userService.updateRole(id, roleDTO.getId());
        return flag==1?Result.success("分配成功"):Result.fail("分配失败");
    }

    @GetMapping("/info")
    @RequiresAuthentication
    public Result info(){
        Long id = ShiroUtil.getProfile().getId();
        return Result.success(userService.get(id).setPassword(""));
    }

    @PostMapping("/alter")
    public Result alter(@RequestBody User user){
        int flag = userService.update(user);
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }

    @PostMapping("/password")
    public Result password(@RequestBody PwdDTO pwd){
        int flag = userService.updatePassword(pwd.getId(),pwd.getPassword());
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }


    @GetMapping("/perm")
    //@RequiresRoles("admin") //访问此方法必须拥有的角色
    @RequiresPermissions("不可能会有的权限") //访问此方法必须具备的权限
    public Result perm(){
        System.err.println("你是超级管理员吗，不然的话，你是不可能会有这个方法的权限的");
        return Result.success("你是超级管理员吗，不然的话，你是不可能会有这个方法的权限的");
    }


    @GetMapping("/test")
    @RequiresPermissions("sys:list:info")
    public Result home(){
        return Result.fail("");
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        if(file==null){
            return Result.fail("图片为空");
        }

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
        double width = bi.getWidth();
        int h = (int) (bi.getHeight()*(w/width));

        ImageUtil.changeSize(w,h,upload+"//"+imgName);

        return avatar();

    }

    @GetMapping("/avatar")
    public Result avatar() throws IOException {
        long id = ShiroUtil.getProfile().getId();
        File file = new File("D:\\upload\\avatar\\" + id + ".png");

        byte[] data = null;
        try (InputStream is = new FileInputStream("D:\\upload\\avatar\\" + id + ".png")){
            data = new byte[is.available()];
            is.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Encoder encoder = Base64.getEncoder();
        return Result.success(encoder.encodeToString(data));
    }

}
