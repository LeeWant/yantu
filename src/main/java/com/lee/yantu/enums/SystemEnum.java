package com.lee.yantu.enums;

/**
 * 异常状态码
 */
public enum SystemEnum implements CodeEnum {
    TOKEN_EXP(-2,"token过期"),
    TOKEN_ERR(-3,"token错误"),
    UNKNOWN_ERROR(-1,"未定义的错误，请在服务器查看"),
    UPLOAD_TOO_BIG(401,"请上传小于2M的文件"),
    FILE_IS_EMPTY(402,"文件为空，上传失败"),
    TAG_FORMAT_ERR(403,"标签请使用1-20位utf-8字符构成"),
    NULL_STRING(404,"空数据传入"),
    NO_THIS_FLAG(405,"无法识别的flag，操作失败"),
    CAN_NOT_UPLOAD(406,"每个贴子不能上传超过5张图片"),
    NOT_FOUND(407,"没有对应id的数据"),
    IDCARD_ERROR(408,"身份证格式错误"),
    VERIFIED(409,"该用户已验证"),
    KEYWORD_ERROR(410,"关键字错误"),
    ;

    private Integer code;
    private String msg;

    SystemEnum(Integer code, String msg) {
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
