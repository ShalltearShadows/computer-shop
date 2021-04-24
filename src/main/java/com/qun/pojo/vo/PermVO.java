/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/9
 * @Time: 18:03
 */
package com.qun.pojo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PermVO {
    private int permId;
    private int parentId;
    private String name;
    private int level;
    private List<PermVO> children;

    public PermVO(int permId, int parentId, String name, int level) {
        this.permId = permId;
        this.parentId = parentId;
        this.name = name;
        this.level = level;
    }

    public void setChildren(PermVO children) {
        if (this.children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(children);
    }

    public void setPerms(List<PermVO> perms){
        this.children = perms;
    }
}
