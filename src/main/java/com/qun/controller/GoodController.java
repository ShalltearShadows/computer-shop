/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 16:40
 */
package com.qun.controller;

import com.qun.common.lang.Result;
import com.qun.pojo.dto.ForegroundMainListDTO;
import com.qun.pojo.dto.GoodDTO;
import com.qun.pojo.entity.Computer;
import com.qun.service.ComputerService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        computerService.add(computer);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        return computerService.upload(file);
    }


    @PostMapping("/delete")
    public Result delete(@RequestBody Computer computer){
        int flag = computerService.delete(computer.getId());
        return flag==1?Result.success("删除成功"):Result.fail("删除失败");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Computer computer){
        computerService.update(computer);
        return Result.success();
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

    @GetMapping(value = "/image/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable("id") String image) throws IOException {
        File file = new File("D:\\upload\\computer\\"+image);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();
        return bytes;
    }
}
