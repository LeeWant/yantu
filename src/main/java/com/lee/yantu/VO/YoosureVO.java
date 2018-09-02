package com.lee.yantu.VO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class YoosureVO {
    private Integer yoosureId;

    private String nickName;

    private Integer userId;

    private String headImg;

    private String title;

    private String targetSchool;

    private String place;

    private Integer peopleNum;

    private Integer joinedNum;

    private Date goDate;

    private Date creDate;

    private Integer agreeNum;

    private String remark;

    private BigDecimal cost;

    private List<TagVO> tagVOS;

    private List<String> imgName;

    private List<CommentVO> commentVOS;
}
