package com.fsAdmin.modules.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7845420009558844007L;

    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200,"success",data);
    }
    public static <T> Result<T> success(String msg,T data) {
        return new Result<>(200,msg,data);
    }
    public static <T> Result<T> success() {
        return new Result<>(200,"操作成功",null);
    }
    public static <T> Result<T> success(Integer code,String msg,T data) {
        return new Result<>(code,msg,data);
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(500,"fail",data);
    }
    public static <T> Result<T> error(String msg) {
        return new Result<>(500,msg,null);
    }
    public static <T> Result<T> error(Integer code,String msg) {
        return new Result<>(code,msg,null);
    }
    public static <T> Result<T> error() {
        return new Result<>(500,"操作失败",null);
    }
    public static <T> Result<T> error(Integer code,String msg,T data) {
        return new Result<>(code,msg,data);
    }

}
