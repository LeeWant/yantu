package com.lee.yantu.Controller;


import com.lee.yantu.Entity.Evaluate;
import com.lee.yantu.VO.Result;
import com.lee.yantu.service.EvaluateService;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 完成游学后进行评价
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;

    @PostMapping("/add")
    public Result add(@Valid Evaluate evaluate,
                      BindingResult bindingResult){
        return ResultUtil.success(evaluateService.add(evaluate,bindingResult));
    }



}
