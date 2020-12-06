/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 15:45
 */
package com.qun.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(chain = true) //链式写法
public class Order implements Serializable {
    private long id;
    private String price;
    private boolean payment;
    private boolean deliver;
    private Timestamp time;
    private String address;
}
