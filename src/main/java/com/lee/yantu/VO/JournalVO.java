package com.lee.yantu.VO;

import lombok.Data;

@Data
public class JournalVO {
    private Integer journalId;

    private String userName;

    private Integer userId;

    private String title;

    private String url;

    private Integer agreeNum;

    private Integer commentNum;

}
