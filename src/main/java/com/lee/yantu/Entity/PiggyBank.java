package com.lee.yantu.Entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "piggy_bank")
@Entity
public class PiggyBank {
    @Id
    @GeneratedValue
    private Integer bankId;
    @NotNull(message = "用户userId不能为空")
    private Integer userId;
    //已有金额
    private BigDecimal money = new BigDecimal(0);

    //目标金额
    @Range(min = 100, max = 1000000, message = "目标金额应大于100，小于1,000,000")
    @NotNull(message = "目标金额不能为空")
    private BigDecimal targetMoney;

    private Date creDate;

    @Future(message = "截止日期应该在当前日期之后")
    @NotNull(message = "截止日期不能为空")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date targetDate;

    @NotBlank(message = "描述(note)不能为空")
    @NotNull(message = "描述(note)不能为空")
    private String note;

    private Integer isSave = 1;

    private Integer isDelete = 0;
}
