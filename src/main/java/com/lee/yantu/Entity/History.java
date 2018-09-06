package com.lee.yantu.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_history")
@Entity
@Data
public class History {
    @Id
    @GeneratedValue
    private Integer historyId;

    private Integer userId;

    private Integer yoosureId;

    private Date creDate;
    /**
     * 是否结束，默认值为0
     */
    private Integer isFinish = 0;

    /**
     * 用户进行状态，0(用户不在该yoosure内,曾经加入后退出)，1为默认值正在进行
     */
    private Integer opFlag = 1;
    /**
     * 是否评价，默认值为0，游学贴结束后(isFinish = 1)才能评价
     */
    private Integer evaluateId = 0;

    public History() {
    }
}
