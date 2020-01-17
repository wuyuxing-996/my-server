package com.wyx.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User{

    private String name;
    private Integer age;
    private String job;
    private String email;
}
