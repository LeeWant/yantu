package com.lee.yantu.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JournalVO {
    private Integer journalId;

    private String userName;

    private String headImg;

    private Integer userId;

    private String title;

    private String filePath;

    private String url;

    private String html;

    private Date creDate;

    private Integer agreeNum;

    private Integer commentNum;

    private Integer isOpen;

    private List<TagVO> tagVOS;

    private List<CommentVO> commentVOS;
}
