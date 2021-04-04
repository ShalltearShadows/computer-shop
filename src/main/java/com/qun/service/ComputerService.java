/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/12/13
 * @Time: 17:03
 */
package com.qun.service;

import com.qun.common.lang.Result;
import com.qun.pojo.dto.ForegroundMainListDTO;
import com.qun.pojo.entity.Computer;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComputerService {

    List<Computer> getAll(int start,int num, String query);

    List<ForegroundMainListDTO> getFFL(int start);

    int getTotal();

    Computer get(long id);

    int add(Computer computer);

    int delete(long id);

    int update(Computer computer);

    List<ForegroundMainListDTO> query(String info);

    Result upload(MultipartFile file);

}
