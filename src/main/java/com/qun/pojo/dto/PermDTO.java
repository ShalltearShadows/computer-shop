/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/9
 * @Time: 18:03
 */
package com.qun.pojo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermDTO {
    private int id;
    private int parentId;
    private String roleName;
    private List<PermDTO> children;

    public PermDTO(int id,int parentId, String roleName) {
        this.id = id;
        this.parentId = parentId;
        this.roleName = roleName;
    }

    public void setChildren(PermDTO children) {
        if (this.children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(children);
    }
}
