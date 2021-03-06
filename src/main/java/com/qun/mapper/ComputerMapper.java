/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.pojo.vo.ForegroundMainListVO;
import com.qun.pojo.entity.Computer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComputerMapper {

    List<Computer> getAll(@Param("start") int start, @Param("num") int num, @Param("query") String query);

    List<ForegroundMainListVO> getFFL(int start);

    int getTotal(@Param("start") int start, @Param("num") int num, @Param("query") String query);

    Computer get(long id);

    int add(Computer computer);

    int delete(long id);

    int update(Computer computer);

    int upload(@Param("image") String image);

    List<ForegroundMainListVO> query(String info);

}
