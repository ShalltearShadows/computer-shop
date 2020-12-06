/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/1
 * @Time: 22:04
 */
package com.qun.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private Long mobile;
    private Integer role;
    private String address;
}
