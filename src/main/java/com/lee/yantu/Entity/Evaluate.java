package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "evaluate_info")
@Entity
@Data
public class Evaluate {
    @Id
    @GeneratedValue
    private Integer evaluateId;

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotNull(message = "游学贴id不能为空")
    private Integer yoosureId;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creDate;

    @Max(value = 5,message = "最大星值不能大于5")
    @Min(value = 0,message = "最小星值不能小于0")
    private Integer star;

}
