package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.entity.vo.CartOrderVO;
import com.qun.service.OrderService;
import com.qun.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result getAllOrder(){
        return Result.success(orderService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteOrder(@PathVariable("id") Long id){
        orderService.delete(id);
        return Result.success("success");
    }
}
