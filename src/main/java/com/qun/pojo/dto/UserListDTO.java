/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/8
 * @Time: 15:06
 */
package com.qun.pojo.dto;

import com.qun.pojo.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserListDTO {
    private List<User> users;
    private int total;
}