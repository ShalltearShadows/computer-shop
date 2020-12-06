/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 15:44
 */
package com.qun.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true) //链式写法
public class Computer implements Serializable {
    private long id;
    private String brand;
    private String cpu;
    private String gpu;
    private String screen;
    private String memory;
    private String hardDisk;
    private String price;
    private String stock;
}
