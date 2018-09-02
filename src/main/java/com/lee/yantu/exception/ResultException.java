package com.lee.yantu.exception;


import com.lee.yantu.enums.CodeEnum;
import com.lee.yantu.enums.SystemEnum;

/**
 * 定义自己的异常捕获
 */
public class ResultException extends RuntimeException {
    private Integer code;


    public ResultException(CodeEnum codeEnum){
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }
    public ResultException(CodeEnum codeEnum, String addmsg){
        super(codeEnum.getMsg()+addmsg);
        this.code = codeEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
