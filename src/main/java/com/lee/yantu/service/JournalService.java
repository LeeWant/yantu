package com.lee.yantu.service;

import com.lee.yantu.Entity.Journal;
import com.lee.yantu.VO.JournalVO;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Lee
 * @Description: 日记接口类
 * @Date: created in 9:29 2018/8/10
 */
public interface JournalService {


    JournalVO publishJournal(Integer userId, String title, MultipartFile html, MultipartFile img[], Integer[] tagIdArr,Integer isOpen);

    void delete(Integer journalId,HttpServletRequest request);

    JournalVO getOne(Integer journalId, HttpServletRequest request);

    List<JournalVO> getAllByUserId(Integer userId,HttpServletRequest request);

    JournalVO changeStatus(Integer journalId,HttpServletRequest request);




}
