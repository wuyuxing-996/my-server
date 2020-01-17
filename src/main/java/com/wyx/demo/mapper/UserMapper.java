package com.wyx.demo.mapper;

import com.wyx.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    User getById(Integer id);
    
    @Select("select * from user")
    List<User> list();
}
