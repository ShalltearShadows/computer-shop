/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/9
 * @Time: 18:03
 */
package com.qun.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermVO {
    private int id;
    private int parentId;
    private String roleName;
    private List<PermVO> children;

    public PermVO(int id, int parentId, String roleName) {
        this.id = id;
        this.parentId = parentId;
        this.roleName = roleName;
    }

    public void setChildren(PermVO children) {
        if (this.children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(children);
    }
}
