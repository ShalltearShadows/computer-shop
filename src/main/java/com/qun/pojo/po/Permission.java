/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 15:45
 */
package com.qun.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Permission implements Serializable {
    private int permId;
    private int parentId;
    private String name;
    private String perm;
    private String icon;
    private String level;

}
