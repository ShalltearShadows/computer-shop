/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 16:40
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.entity.dto.ForegroundMainListDTO;
import com.qun.entity.dto.GoodDTO;
import com.qun.entity.po.Computer;
import com.qun.service.ComputerService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @PostMapping("/add")
    @RequiresAuthentication
    public Result add(@RequestBody Computer computer){
        int flag = computerService.add(computer);
        System.out.println(computer.toString());
        return flag==1?Result.success("添加成功"):Result.fail("添加失败");
    }


    @PostMapping("/upload")
    public Result upload(MultipartFile file){

        if(file==null){
            return Result.fail("图片为空");
        }

        String upload = "D:\\upload\\computer";

        String name = file.getOriginalFilename();

        assert name != null;

        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            id.append((int) (Math.random() * 10) + 1);
        }

        String suffix = name.split("\\.")[1];

        String imgName = id+"."+suffix;

        File webImgage = new File(upload,imgName);

        try {
            file.transferTo(webImgage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(imgName);
    }


    @PostMapping("/delete")
    public Result delete(@RequestBody Computer computer){
        int flag = computerService.delete(computer.getId());
        return flag==1?Result.success("删除成功"):Result.fail("删除失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Computer computer){
        int flag = computerService.update(computer);
        return flag==1?Result.success("修改成功"):Result.fail("修改失败");
    }

    @GetMapping("/fml")
    public Result getFFL(@RequestParam("num") int num){
        List<ForegroundMainListDTO> ffl = computerService.getFFL((num-1)*5);
        return Result.success(ffl);
    }

    @GetMapping("/{id}")
    public Result getGood(@PathVariable("id") long id){
        Computer computer = computerService.get(id);
        return Result.success(computer);
    }

    @GetMapping("/query")
    public Result query(@RequestParam("info") String info){
        List<ForegroundMainListDTO> query = computerService.query(info);
        return Result.success(query);
    }
}
