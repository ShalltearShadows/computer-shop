/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/11/29
 * @Time: 16:32
 */
package com.qun.mapper;

import com.qun.pojo.po.Right;
import com.qun.pojo.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RightMapper {
    List<Right> get();
}
