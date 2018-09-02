package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * 标签细节
 * @Author: Lee
 * @Description:
 * @Date: created in 22:30 2018/5/22
 */
@Entity
@Data
@Table(name = "tag_details")
public class Tag {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer tagId;

    private Integer userId;

    private Integer yoosureId;

    private Integer journalId;
    /** 创建时间 */
    private Date creDate;
    /** 标签信息 */
    private String tagInfo;
    /** 1表示用户标签，2表示游学标签，3表示手账标签 ,0表示该标签不存在*/
    private Integer flag = 0;

    public Tag() {
    }
}
