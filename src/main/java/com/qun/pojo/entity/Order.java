/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 15:45
 */
package com.qun.pojo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true) //链式写法
public class Order implements Serializable {
    private Long id;
    private Long userId;
    private Long goodId;
    private Integer count;
    private Timestamp time;
    private BigDecimal total;
    private Integer pay;
}
