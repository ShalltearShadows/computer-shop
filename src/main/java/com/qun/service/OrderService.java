package com.qun.service;

import com.qun.entity.vo.CartOrderVO;
import com.qun.entity.dao.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order get(long id);
    int add(CartOrderVO order);
    int delete(long id);
    int update(Order order);
}
