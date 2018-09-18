package com.lee.yantu.VO;

import lombok.Data;

import java.util.List;

@Data
public class JournalVO {
    private Integer journalId;

    private String userName;

    private Integer userId;

    private String title;

    private String filePath;

    private String url;

    private String html;

    private Integer agreeNum;

    private Integer commentNum;

    private Integer isOpen;

    private List<TagVO> tagVOS;

    private List<CommentVO> commentVOS;
}
