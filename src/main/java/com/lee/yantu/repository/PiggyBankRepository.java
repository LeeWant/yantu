package com.lee.yantu.repository;

import com.lee.yantu.Entity.PiggyBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PiggyBankRepository extends JpaRepository<PiggyBank,Integer> {
    List<PiggyBank> findByUserIdAndIsSave(Integer userId ,Integer isSave);

    List<PiggyBank> findByUserIdAndIsSaveNotIn(Integer userId,Integer isSave);

    List<PiggyBank> findByIsSaveAndIsDelete(Integer isSave,Integer isDelete);
}
