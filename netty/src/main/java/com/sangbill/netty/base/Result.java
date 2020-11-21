package com.sangbill.netty.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private final static Integer OK = 0;
    private final static Integer FAIL = -1;

    private Integer code;
    private String msg;
    private T data;


    public static Result success(String msg) {
        return new Result(OK,msg,null);
    }

    public static <T>  Result<T> success(String msg,T data) {
        return new Result(OK,msg,data);
    }


    public static Result fail(String msg) {
        return new Result(FAIL,msg,null);
    }

    public static <T>  Result<T> custom(Integer code,String msg,T data) {
        return new Result(code,msg,data);
    }

    public boolean isOK() {
        return code == OK;
    }
}
