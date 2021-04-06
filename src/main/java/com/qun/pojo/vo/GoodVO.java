/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 17:18
 */
package com.qun.pojo.vo;

import com.qun.pojo.entity.Computer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GoodVO {
    private List<Computer> goods;
    private int total;
}
