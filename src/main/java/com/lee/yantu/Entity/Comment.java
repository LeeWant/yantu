package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "comment")
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Integer commentId;

    /** 发表评论的用户名 */
    private Integer userId;
    /** 评论的游学贴id */
    private Integer yoosureId;
    /** 评论的手账id */
    private Integer journalId;
    /** 评论的内容 */
    private String content;
    /** 评论日期 */
    private Date creDate;
    /** 2表示游学贴，3表示手账 */
    private Integer flag;
    /** 1表示已被删除 */
    private Integer isDelete = 0;

}
