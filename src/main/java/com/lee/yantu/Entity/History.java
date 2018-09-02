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

    private Integer isFinish;

    /**
     * 用户进行状态，0(用户不在该yoosure内)，1为默认值正在进行
     */
    private Integer opFlag = 1;

    public History() {
    }
}
