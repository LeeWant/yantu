package com.lee.yantu.handle;

import com.lee.yantu.VO.Result;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.ResultUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof ResultException){
            ResultException resultException = (ResultException) e;
            return ResultUtil.error(resultException.getCode(),resultException.getMessage());
        }else {
            logger.error("【系统异常】",e);
            return ResultUtil.error(-1,e.getMessage());
        }
    }
}
