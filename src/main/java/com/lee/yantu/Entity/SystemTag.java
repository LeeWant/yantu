package com.lee.yantu.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 标签的管理库，所有的系统定义的标签存放于此（不包括用户自定义的）
 * @Author: Lee
 * @Description:
 * @Date: created in 16:13 2018/5/17
 */
@Table(name = "tag")
@Entity
@Data
public class SystemTag {

    /** 标签id */
    @Id
    @GeneratedValue
    private Integer tagId;
    /** 标签内容 */
    private String tagInfo;
    /** 所属类别1为用于用户，2为用于游学贴，3为用于手账 */
    private Integer tagFlag;
    /** 创建日期 */
    private Date creDate;
    /** 标签的具体描述(可以是获取条件等) */
    private String tagDec;

    public SystemTag() {
    }
}
