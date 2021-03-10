/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 15:45
 */
package com.qun.entity.dao;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;
    private String password;
    private Long mobile;

    @NotBlank(message = "地址不能为空")
    private String address;
    private Integer role;
    private Integer status;
}
