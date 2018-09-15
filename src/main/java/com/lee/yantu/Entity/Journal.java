package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "journal")
@Entity
@Data
public class Journal {
    @Id
    @GeneratedValue
    private Integer journalId;

    private Integer userId;

    private String title;

    private String content;

    private Date creDate;

    private Integer agreeNum = 0;

    private Integer isDelete = 0;

    private Integer isOpen;
}
