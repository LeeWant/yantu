package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "banner_info")
@Entity
@Data
public class Banner {
    @Id
    @GeneratedValue
    private Integer BannerId;

    private String img;

    private String link;

    private Date creDate;

    private Date opDate;

    private Integer isOnline = 0;

    private Integer isDelete = 0;

}
