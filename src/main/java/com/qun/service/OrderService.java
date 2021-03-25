package com.qun.service;

import com.qun.common.lang.Result;
import com.qun.pojo.vo.CartOrderVO;
import com.qun.pojo.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll(int start,int num,String query);
    Order get(long id);
    int add(CartOrderVO order);
    int delete(long id);
    int update(Order order);
    int getTotal(String query);
    Result pay(CartOrderVO cartOrderVO);
}
