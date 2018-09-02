package com.lee.yantu.VO;

import lombok.Data;

import java.util.List;

@Data
public class Result<T> {
    /** 错误码 */
    private Integer code;
    /** 错误信息 */
    private String msg;
    /** 数据内容 */
    private T data;
}
