package com.lee.yantu.service;

import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.VO.YoosureSimpleVO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface YoosureService {

    Page<Yoosure> getPageByAgreeNum(Integer page);

    YoosureSimpleVO agreeAddOne(Integer id,HttpServletRequest request );

    Yoosure save(Yoosure yoosure);

    Yoosure publishYoosure(Yoosure yoosure,BindingResult bindingResult,Integer[] tagIdArr);

    Page<Yoosure> getPage(Integer page);

    void deleteByYoosureId(Integer yoosureId);

    void join(Integer userId,Integer yoosureId);

    void quit(Integer userId);

    Yoosure findOne(Integer yoosureId);

    void finish(Yoosure yoosure);

    List<Yoosure> findAllCanJoin();

    List<Yoosure> findAllNotFinish();

}
