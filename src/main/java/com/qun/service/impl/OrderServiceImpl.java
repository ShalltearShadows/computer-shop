package com.qun.service.impl;

import com.qun.pojo.vo.CartOrderVO;
import com.qun.pojo.entity.Order;
import com.qun.mapper.OrderMapper;
import com.qun.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getAll(int start,int num,String query) {
        return orderMapper.getAll(start,num,query);
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

    @Override
    public int getTotal(int start,int num,String query) {
        return orderMapper.getTotal(start, num, query);
    }
}
