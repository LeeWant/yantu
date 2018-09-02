package com.lee.yantu.VO;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Data
public class CommentVO {
    private Integer commentId;

    private String content;

    private String nickName;

    private Integer userId;

    private Date creDate;

    private String headImg;

}
