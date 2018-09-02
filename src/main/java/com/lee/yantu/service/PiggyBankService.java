package com.lee.yantu.service;

import com.lee.yantu.Entity.BankDetails;
import com.lee.yantu.Entity.PiggyBank;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.List;

public interface PiggyBankService {

    PiggyBank save(PiggyBank piggyBank);

    List<PiggyBank> findNotFinish();

    /**
     * 获取该用户的所有存钱罐
     * @param userId
     * @return
     */
    List<PiggyBank> findByUserId(Integer userId);

    /**
     * 获取一个存钱罐的存取历史
     * @param bankId
     * @return
     */
    Page findDetails(Integer bankId, Integer page);

    /**
     * 根据bankId获取存钱罐
     * @param bankId
     * @return
     */
    PiggyBank findOne(Integer bankId);

    /**
     * 添加一个存钱罐
     * @param bank
     * @return
     */
    PiggyBank add(PiggyBank bank, BindingResult bindingResult);

    /**
     * 删除一个存钱罐
     * @param bankId
     * @return
     */
    void delete(Integer bankId);

    /**
     * 存钱
     * @return
     */
    PiggyBank raiseMoney(Integer bankId,Integer raiseMoney);

    /**
     * 取钱
     * @return
     */
    PiggyBank reduceMoney(Integer bankId,Integer reduceMoney);

}
