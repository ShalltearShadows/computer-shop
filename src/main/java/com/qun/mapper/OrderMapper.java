/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.pojo.vo.CartOrderVO;
import com.qun.pojo.entity.Order;
import com.qun.pojo.vo.CartShowVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    List<Order> getAll(int start,int num,String query);
    Order get(long id);
    int add(CartOrderVO order);
    int delete(long id);
    int update(Order order);
    int getTotal(String query);
    List<CartShowVO> getCartOrder(Long id);
    List<Order> getPersonOrder(int start,int num,String query,long id);
    int getPersonOrderTotal(String query);
}
