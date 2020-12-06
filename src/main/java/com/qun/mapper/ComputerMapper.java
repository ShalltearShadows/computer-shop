/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.entity.po.Computer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComputerMapper {
    List<Computer> getAll();
    Computer get(long id);
    int add(Computer computer);
    int delete(long id);
    int update(Computer computer);
}
