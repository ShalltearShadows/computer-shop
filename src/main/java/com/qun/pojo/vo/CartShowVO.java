package com.qun.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author Shadow（li qun）
 * @Date 2021/4/6
 */

@Data
@Accessors(chain = true)
public class CartShowVO implements Serializable {
    private Long goodId;
    private Integer count;
    private String url;
}
