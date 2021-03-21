/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 15:44
 */
package com.qun.pojo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private BigDecimal price;
    private int stock;
    private String image;
}
