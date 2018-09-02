package com.lee.yantu.VO;

import lombok.Data;

@Data
public class TagVO {
    private String tagInfo;

    private Integer tagId;

    public TagVO(){}

    public TagVO(String tagInfo){
        this.tagInfo = tagInfo;
    }

    public TagVO(String tagInfo,Integer tagId) {
        this.tagInfo = tagInfo;
        this.tagId = tagId;
    }
}
