package com.lee.yantu.service;

import com.lee.yantu.Entity.Journal;
import org.springframework.validation.BindingResult;

/**
 * @Author: Lee
 * @Description: 日记接口类
 * @Date: created in 9:29 2018/8/10
 */
public interface JournalService {

    /**
     * 发布日记
     * @param journal
     * @param bindingResult
     * @param tagIdArr
     * @return
     */
    Journal publishJournal(Journal journal, BindingResult bindingResult,Integer[] tagIdArr);


    void delete(Integer journalId);

    Journal getOne(Integer journalId);



}
