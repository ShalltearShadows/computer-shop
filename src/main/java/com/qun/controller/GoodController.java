/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 16:40
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.entity.dto.GoodDTO;
import com.qun.entity.po.Computer;
import com.qun.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private ComputerService computerService;

    @GetMapping("/list")
    public Result getAll(@RequestParam("query") String query,@RequestParam("pagesize") int size,@RequestParam("pagenum") int num){
        int start = (num-1)*size;

        List<Computer> all = computerService.getAll(start, size, query);

        int total = computerService.getTotal();

        return Result.success(new GoodDTO().setGoods(all).setTotal(total));
    }
}
