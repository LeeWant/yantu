package com.lee.yantu.enums;

public enum BankEnum implements CodeEnum {
    NO_MORE_BANK(610,"超过用户最大创建数(每个用户最多5个)"),
    NOT_FIND(611,"存钱罐不存在或已被删除(isDelete=1)"),
    CAN_NOT_SAVE(612,"存钱罐不可存(isSave!=1)"),
    NEED_RIGHT_NUM(613,"存钱金额不能小于0或大于100,000"),
    CAN_NOT_REDUCE(614,"取出金额不能大于已存金额")
    ;

    private Integer code;
    private String msg;

    BankEnum(Integer code, String msg) {
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
