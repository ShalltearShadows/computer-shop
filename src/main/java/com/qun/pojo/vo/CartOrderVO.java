package com.qun.pojo.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class CartOrderVO implements Serializable {
    private Long id;
    private Long userId;
    private Long goodId;
    private Integer count;
    private Timestamp time;
    private BigDecimal total;
    private Integer pay;
}
