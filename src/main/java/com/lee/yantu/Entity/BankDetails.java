package com.lee.yantu.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "bank_details")
public class BankDetails {
    @Id
    @GeneratedValue
    private Integer detailId;

    private Integer bankId;

    private BigDecimal money;

    private BigDecimal balance;

    private Date opDate;
}
