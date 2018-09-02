package com.lee.yantu.repository;

import com.lee.yantu.Entity.BankDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankDetailsRepository extends JpaRepository<BankDetails,Integer> {

    Page findByBankId(Pageable pageable,Integer bankId);

}
