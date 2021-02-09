/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 17:05
 */
package com.qun.service;

import com.qun.entity.dto.ForegroundMainListDTO;
import com.qun.entity.po.Computer;
import com.qun.mapper.ComputerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComputerServiceImpl implements ComputerService{

    @Autowired
    private ComputerMapper computerMapper;

    @Override
    public List<Computer> getAll(int start, int num, String query) {
        return computerMapper.getAll(start, num, query);
    }

    @Override
    public List<ForegroundMainListDTO> getFFL() {
        return computerMapper.getFFL();
    }

    @Override
    public int getTotal() {
        return computerMapper.getTotal();
    }

    @Override
    public Computer get(long id) {
        return null;
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
    public int upload(String image) {
        return computerMapper.upload(image);
    }
}
