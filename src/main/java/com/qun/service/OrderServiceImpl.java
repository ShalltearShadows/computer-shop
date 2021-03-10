package com.qun.service;

import com.qun.entity.vo.CartOrderVO;
import com.qun.entity.dao.Order;
import com.qun.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getAll() {
        return orderMapper.getAll();
    }

    @Override
    public Order get(long id) {
        return orderMapper.get(id);
    }

    @Override
    public int add(CartOrderVO order) {
        return orderMapper.add(order);
    }

    @Override
    public int delete(long id) {
        return orderMapper.delete(id);
    }

    @Override
    public int update(Order order) {
        return orderMapper.update(order);
    }
}
