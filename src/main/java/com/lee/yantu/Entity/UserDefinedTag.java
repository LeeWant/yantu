package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户自定义标签库
 * @Author: Lee
 * @Description:
 * @Date: created in 16:15 2018/5/17
 */
@Entity
@Table(name = "self_tag")
@Data
public class UserDefinedTag {
    /** 标签id */
    @Id
    @GeneratedValue
    private Integer tagId;
    /** 用户id */
    private Integer userId;
    /** 标签内容 */
    private String tagInfo;
    /** 标签创建时间 */
    private Date creDate;

    public UserDefinedTag() {
    }

    public UserDefinedTag(String tagInfo) {
        this.tagInfo = tagInfo;
    }
}
