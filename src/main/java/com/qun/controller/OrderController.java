package com.qun.controller;

import cn.hutool.core.map.MapUtil;
import com.qun.common.lang.Result;
import com.qun.pojo.entity.Order;
import com.qun.pojo.entity.User;
import com.qun.pojo.vo.CartOrderVO;
import com.qun.service.OrderService;
import com.qun.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @RequiresPermissions("order:list:add")
    public Result addCart(@RequestBody CartOrderVO cartOrderVO){
        cartOrderVO.setUserId(ShiroUtil.getProfile().getId());
        orderService.add(cartOrderVO);
        return Result.success(cartOrderVO.getId());
    }

    @GetMapping("/all")
    @RequiresPermissions("order:list:query")
    public Result getAllOrder(@RequestParam("query") String query,@RequestParam("pagesize") int size,@RequestParam("pagenum") int num){
        return orderService.getAllOrder("".equals(query)?null:query,(num-1)*size,size);
    }

    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("order:list:delete")
    public Result deleteOrder(@PathVariable("id") Long id){
        orderService.delete(id);
        return Result.success("success");
    }

    @PostMapping("/pay")
    @RequiresPermissions("order:list:pay")
    public Result pay(@RequestBody CartOrderVO orderVO){
        return orderService.pay(orderVO);
    }

    @PostMapping("/update")//返回给支付宝
    public String update(HttpServletRequest request){
        orderService.setPay(request.getParameter("out_trade_no"));
        return "success";
    }

    @GetMapping("/getCart")
    public Result getCart(){
        return orderService.getCart();
    }
}
