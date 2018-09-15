package com.lee.yantu.enums;


/**
 * @Author: Lee
 * @Description:对户用进行操作时可能出现的错误 200
 * @Date: created in 22:35 2018/5/14
 */
public enum UserEnum implements CodeEnum{
    REGISTER_ERR(201,"用户注册时错误--"),
    USER_NOT_REGISTER(202,"该邮箱未注册"),
    PASSWORD_ERR(203,"登陆密码错误"),
    USER_EXIST(204,"该邮箱已注册"),
    UPDATE_MISTAKE(205,"更新失败,未获取到实际参数"),
    PASSWORD_MISSION(206,"请同时输入新密码和旧密码"),
    USER_NOT_FOUND(207,"没有找到该用户"),
    PASSWORD_FORMAT_ERR(208,"密码格式不正确，密码由6-16位字母、数字、下划线构成"),
    EMAIL_FORMAT_ERR(209,"邮箱格式不正确"),
    NICKNAME_FORMAT_ERR(210,"用户昵称不正确,昵称由3-20位不含非法字符的字符串构成"),
    ORIGINAL_PASSWORD_ERR(211,"原始密码错误，请重新确认"),
    TAG_LIMIT(212,"用户自定义标签最多不能超过5个"),
    NICKNAME_EXIST(213,"该昵称已存在"),
    LACK_PARAMETER(214,"缺少参数"),
    USER_NOT_JOINED(215,"该用户没有加入目标游学"),
    USER_NOT_EXIST(216,"用户不存在"),
    ;
    private Integer code;

    private String msg;

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
