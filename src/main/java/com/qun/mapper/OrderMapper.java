/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.entity.vo.CartOrderVO;
import com.qun.entity.dao.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    List<Order> getAll();
    Order get(long id);
    int add(CartOrderVO order);
    int delete(long id);
    int update(Order order);
}
