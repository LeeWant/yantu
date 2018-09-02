package com.lee.yantu.enums;

public enum CommentEnum implements CodeEnum {
    COMMENT_NOT_FOUND(501,"没有找到这个评论"),
    COMMENT_IS_DELETE(502,"评论已被删除"),

    ;

    private Integer code;
    private String msg;

    CommentEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
