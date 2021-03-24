package com.qun.controller;

import cn.hutool.core.map.MapUtil;
import com.qun.common.lang.Result;
import com.qun.pojo.entity.Order;
import com.qun.pojo.entity.User;
import com.qun.pojo.vo.CartOrderVO;
import com.qun.service.OrderService;
import com.qun.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Result addCart(@RequestBody CartOrderVO cartOrderVO){
        cartOrderVO.setUserId(ShiroUtil.getProfile().getId());

        orderService.add(cartOrderVO);
        return Result.success(cartOrderVO.getId());
    }

    @GetMapping("/all")
    public Result getAllOrder(@RequestParam("query") String query,@RequestParam("pagesize") int size,@RequestParam("pagenum") int num){

        int start = (num-1)*size;

        List<Order> all = orderService.getAll(start, size, "".equals(query)?null:query);
        int total = orderService.getTotal(start, size, "".equals(query)?null:query);
        Map<Object, Object> map = MapUtil.builder()
                .put("orders", all)
                .put("total", total)
                .map();
        return Result.success(map);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteOrder(@PathVariable("id") Long id){
        orderService.delete(id);
        return Result.success("success");
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody CartOrderVO orderVO){
        return orderService.pay(orderVO);
    }

    @PostMapping("/update")//返回给支付宝
    public String update(){

        return "success";
    }
}
