package com.lee.yantu.VO;

import lombok.Data;

import java.util.Date;

@Data
public class JournalSimpleVO {

    private Integer journalId;

    private String nickName;

    private String headImg;

    private Integer userId;

    private Date creDate;

    private Integer agreeNum;
}
