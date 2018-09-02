package com.lee.yantu.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "identification")
@Entity
@Data
public class Identification {
    @Id
    @GeneratedValue
    private Integer idenId;
    private Integer userId;
    private String realName;
    private String idCard;
    private String cardImg;
    private String studentCardImg;
    private String studentId;
    private String school;

}
