package com.wyx.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String status;
    private String message;
    private Object data;
    
    public static Result success(Object o) {
        return new Result("1","成功",o);
    }
    
    public static Result fail(Object o) {
        return new Result("0","失败",o);
    }
}
