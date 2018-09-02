package com.lee.yantu.Entity;


import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: Lee
 * @Description:
 * @Date: created in 23:29 2018/5/14
 */
@Table(name = "yoosure")
@Entity
@Data
public class Yoosure {
    /** 主键 */
    @Id
    @GeneratedValue
    private Integer yoosureId;
    /** 用户表主键 */
    @NotNull(message = "创建用户不能为空")
    private Integer userId;
    /** 创建时间 */
    private Date creDate;
    /** 出发时间 */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date goDate;
    /** 预计花费 */
    @Min(message = "不能低于0元",value = 0)
    @Max(message = "不能高于10000元每人",value = 10000)
    private BigDecimal cost;
    /** 最大参加人数 */
    @NotNull(message = "人数不能为空")
    @Min(message = "人数最少不能低于2人",value = 2)
    @Max(message = "人数最多不能超过10人",value = 10)
    private Integer peopleNum;
    /** 当前参与人数 */
    private Integer joinedNum = 1;
    /** 目标院校 */
    @NotBlank(message = "目标院校不能为空")
    private String targetSchool;
    /** 点赞数 */
    private Integer agreeNum = 0;
    /** 标题 */
    @NotBlank(message = "标题不能为空")
    @Size(min = 5,max = 15,message = "标题字数应在5-15")
    private String title;
    /** 备注 */
    private String remark;
    /** 地点 */
    @NotBlank(message = "地点不能为空")
    private String place;
    /** 能否加入的标志，1为可以加入，0为不能加入，默认为1 */
    private Integer isJoin = 1;
    /** 是否删除1为删除，0为未删除，默认为0 */
    private Integer isDelete = 0;
    /** 是否结束的标志，1为结束，0为未结束，默认为0 */
    private Integer isFinish = 0;

    public Yoosure() {
    }
}
