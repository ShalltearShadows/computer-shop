/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 17:18
 */
package com.qun.entity.dto;

import com.qun.entity.po.Computer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GoodDTO {
    private List<Computer> goods;
    private int total;
}
