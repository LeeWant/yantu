package com.lee.yantu.VO;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserVO {
    private Integer userId;

    private String headImg;

    private String nickName;

    private Date birthDate;

    private String School;

    private String sign;

    private String description;

    private Integer joinId;

    private List<TagVO> tags;
}
