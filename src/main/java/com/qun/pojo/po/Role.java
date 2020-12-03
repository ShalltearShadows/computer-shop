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
public class Role implements Serializable {
    private int id;
    private String name;
    private String right;
}
