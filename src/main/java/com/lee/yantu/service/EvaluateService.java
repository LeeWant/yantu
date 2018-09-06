package com.lee.yantu.service;

import com.lee.yantu.Entity.Evaluate;
import org.springframework.validation.BindingResult;

public interface EvaluateService {

    Evaluate add(Evaluate evaluate, BindingResult bindingResult);

    Evaluate getOne(Integer evaluateId);

    Evaluate save(Evaluate evaluate);
}
