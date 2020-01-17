package com.wyx.demo.controller;

import com.wyx.demo.entity.User;
import com.wyx.demo.mapper.DocMapper;
import com.wyx.demo.mapper.UserMapper;
import com.wyx.demo.util.Result;
import com.wyx.demo.util.SendMail;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/front/users")
public class UserController {

    private final UserMapper userMapper;
    private final DocMapper docMapper;
    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
        this.docMapper = null;
    }
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        User user = userMapper.getById(id);
        System.out.println(id);
        return Result.success(user);
    }
    @GetMapping()
    public Result list() {
        Page<User> users = PageHelper.startPage(2, 1).doSelectPage(userMapper::list);
        System.out.println(users);
        Map<String, Object> map = new HashMap<>();
        map.put("total", "012aaa2aa");
        return Result.fail(map);
    }
    @GetMapping("/students")
    public Result test() {
        new Thread(new SendMail("1084151202@qq.com", "777777")).start();
        return list();
    }
}
