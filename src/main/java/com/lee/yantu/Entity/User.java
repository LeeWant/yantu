package com.lee.yantu.Entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_info")
@Entity
@DynamicUpdate
@Data
public class User {
    /** 主键，不是用户名 */
    @Id
    @GeneratedValue
    private Integer userId;
    /** 实名认证的主键id */
    private Integer idenId;
    /** 用户昵称 */
    private String nickName;
    /** 用户邮箱，用于登录 */
    private String email;
    /** 登录密码 */
    private String password;
    /** 用户的学校 */
    private String school;
    /** 性别 */
    private Integer sex;
    /** 出生日期 */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    /** 手机号 */
    private String phone;
    /** 头像的路径 */
    private String headImg;
    /** 用户的个性签名 */
    private String sign;
    /** 用户的自我介绍 */
    private String description;
    /** 最后一次操作时间 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date opDate;
    /** 注册时间 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    /** 获得的点赞数 */
    private Integer agreeCount = 0;
    /** 用户当前加入的游学id默认为0表示尚未加入 */
    private Integer joinId = 0;
    /** 用户信用分 */
    private Integer creditScore = 600;
    /** 是否被删除默认未被删除 */
    private Integer isDelete = 0;

    public User() {
    }


}
