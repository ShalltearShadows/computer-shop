/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 17:05
 */
package com.qun.service.impl;

import com.qun.common.lang.Result;
import com.qun.pojo.dto.ForegroundMainListDTO;
import com.qun.pojo.entity.Computer;
import com.qun.mapper.ComputerMapper;
import com.qun.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class ComputerServiceImpl implements ComputerService {

    @Autowired
    private ComputerMapper computerMapper;

    @Override
    public List<Computer> getAll(int start, int num, String query) {
        return computerMapper.getAll(start, num, query);
    }

    @Override
    public List<ForegroundMainListDTO> getFFL(int start) {
        return computerMapper.getFFL(start);
    }

    @Override
    public int getTotal() {
        return computerMapper.getTotal();
    }

    @Override
    public Computer get(long id) {
        return computerMapper.get(id);
    }

    @Override
    public int add(Computer computer) {
        return computerMapper.add(computer);
    }

    @Override
    public int delete(long id) {
        return computerMapper.delete(id);
    }

    @Override
    public int update(Computer computer) {
        return computerMapper.update(computer);
    }

    @Override
    public List<ForegroundMainListDTO> query(String info) {
        return computerMapper.query(info);
    }

    @Override
    public Result upload(MultipartFile file) {

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

        return Result.success("http://localhost:8080/good/image/"+imgName);
    }

}
