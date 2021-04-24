/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 16:40
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.pojo.vo.ForegroundMainListVO;
import com.qun.pojo.vo.GoodVO;
import com.qun.pojo.entity.Computer;
import com.qun.service.ComputerService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private ComputerService computerService;

    @GetMapping("/list")
    @RequiresPermissions("good:list:query")
    public Result getAll(@RequestParam("query") String query,@RequestParam("pagesize") int size,@RequestParam("pagenum") int num){

        List<Computer> all = computerService.getAll((num-1)*size, size, query);
        int total = computerService.getTotal((num-1)*size, size, query);

        return Result.success(new GoodVO().setGoods(all).setTotal(total));
    }

    @PostMapping("/add")
    @RequiresPermissions("good:add")
    public Result add(@RequestBody Computer computer){
        computerService.add(computer);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        return computerService.upload(file);
    }


    @PostMapping("/delete")
    @RequiresPermissions("good:list:delete")
    public Result delete(@RequestBody Computer computer){
        computerService.delete(computer.getId());
        return Result.success("删除成功");
    }

    @PostMapping("/update")
    @RequiresPermissions("good:list:edit")
    public Result update(@RequestBody Computer computer){
        computerService.update(computer);
        return Result.success();
    }

    @GetMapping("/fml")
    public Result getFFL(@RequestParam("num") int num){
        List<ForegroundMainListVO> ffl = computerService.getFFL((num-1)*5);
        return Result.success(ffl);
    }

    @GetMapping("/{id}")
    public Result getGood(@PathVariable("id") long id){
        Computer computer = computerService.get(id);
        return Result.success(computer);
    }

    @GetMapping("/query")
    public Result query(@RequestParam("info") String info){
        List<ForegroundMainListVO> query = computerService.query(info);
        return Result.success(query);
    }

    @GetMapping(value = "/image/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable("id") String image) throws IOException {
        return computerService.image(image);
    }
}
