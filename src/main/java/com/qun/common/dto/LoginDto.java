/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/2
 * @Time: 12:04
 */
package com.qun.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotNull(message = "用户名不能为空")
    private Long id;

    @NotBlank(message = "密码不能为空")
    private String password;

}
