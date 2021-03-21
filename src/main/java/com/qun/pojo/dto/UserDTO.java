/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/8
 * @Time: 15:31
 */
package com.qun.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class UserDTO{
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2,max = 16,message = "用户名的长度必须在2~16之间")
    private String username;

    @Length(min = 6,max = 16,message = "密码的长度必须在6~16之间")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Digits(integer = 11,fraction = 0,message = "手机号不合法")
    private Long mobile;

    @NotBlank(message = "地址不能为空")
    private String address;
}
