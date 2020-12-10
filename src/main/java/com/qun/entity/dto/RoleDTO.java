/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/9
 * @Time: 20:30
 */
package com.qun.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private int id;
    private String name;
    private int[] perm;

    private List<PermDTO> children;
}
