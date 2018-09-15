package com.lee.yantu.Entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "img")
public class Img {
    @Id
    @GeneratedValue
    private Integer imgId;

    private Integer journalId;

    private Integer yoosureId;

    private String imgPath;
    /** 2表示游学 3表示手账 */
    private Integer flag;

    public Img(Integer journalId, Integer yoosureId, String imgPath, Integer flag) {
        this.journalId = journalId;
        this.yoosureId = yoosureId;
        this.imgPath = imgPath;
        this.flag = flag;
    }

    public Img() {
    }
}
