package com.lee.yantu.enums;

public enum YoosureEnum implements CodeEnum{
    PUBLISH_ERR(301,"参数错误--"),
    CAN_NOT_JOIN_TWO(302,"每个用户同时只能加入一个yoosure"),
    YOOSURE_NOT_EXIST(303,"游学贴不存在"),
    YOOSURE_IS_DELETE(304,"该游学贴已被删除"),
    CAN_NOT_JOIN(305,"加入失败，该游学已关闭招募"),
    YOOSURE_OVERDATE(306,"该游学已过期"),
    LIMIT_FULL(307,"该游学人数已满"),
    YOOSURE_IS_FINISH(308,"该游学已经结束"),
    CAN_NOT_QUIT(309,"加入游学24小时之后才能退出"),
    YOU_CAN_NOT_QUIT(310,"游学创建者无法退出"),
    NOT_CREATOR(311,"该用户不是创建者不能结束游学"),
    CAN_NOT_FINISH(312,"游学开始24小时后才能结束"),
    NOT_FINISH(313,"游学贴没有结束"),
    ONLY_EVALUATE_ONCE(314,"每个用户只能评价一次"),
    DATE_ERROR(315,"游学贴须至少提前于当前日期两日发布"),
    ;

    private Integer code;

    private String msg;

    YoosureEnum(Integer code, String msg) {
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
