package com.wyx.demo.mapper;

import com.wyx.demo.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zving
 * @date 2020/1/13
 * @description
 **/
public interface DocMapper {

    @Select("select * from user")
    List<User> list();
}
