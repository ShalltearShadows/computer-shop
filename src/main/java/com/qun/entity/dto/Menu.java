/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/7
 * @Time: 12:02
 */
package com.qun.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Menu {
    private int id;
    private String name;
    private int level;
    private int parentId;
    private String path;
    private List<Menu> children;

    public Menu(int id,String name,int level,int parentId){
        this.id = id;
        this.name = name;
        this.level = level;
        this.parentId = parentId;
    }

    public void setChildren(Menu children) {
        if (this.children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(children);
    }
}
