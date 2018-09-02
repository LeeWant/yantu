package com.lee.yantu.util;


import com.lee.yantu.VO.Result;
import com.lee.yantu.enums.CodeEnum;

/**
 * 返回状态的封装操作
 */
public class ResultUtil {
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("操作成功!");
        result.setData(object);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg("操作失败:"+msg);
        return result;
    }
    public static Result error(CodeEnum ce){
        return error(ce.getCode(),ce.getMsg());
    }
}
