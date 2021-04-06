/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/9
 * @Time: 20:30
 */
package com.qun.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleVO {
    private int id;
    private String name;
    private int[] perm;

    private List<PermVO> children;
}
