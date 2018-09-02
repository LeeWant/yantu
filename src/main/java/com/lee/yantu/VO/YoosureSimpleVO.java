package com.lee.yantu.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class YoosureSimpleVO {
    /** yoosureId */
    private Integer yoosureId;
    /** 用户头像 */
    private String headImg;
    /** 目的地 */
    private String targetSchool;
    /** 出发日期 */
    private Date goDate;
    /** 集合地点 */
    private String place;

    private BigDecimal cost;

    private String title;

    private List<TagVO> tagVOS;
}
