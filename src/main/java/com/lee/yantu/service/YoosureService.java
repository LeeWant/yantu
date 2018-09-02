package com.lee.yantu.service;

import com.lee.yantu.Entity.Yoosure;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.List;


public interface YoosureService {
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
