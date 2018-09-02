package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.Evaluate;
import com.lee.yantu.Entity.History;
import com.lee.yantu.Entity.User;
import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.enums.YoosureEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.repository.EvaluateRepository;
import com.lee.yantu.repository.HistoryRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.repository.YoosureRepository;
import com.lee.yantu.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private YoosureRepository yoosureRepository;
    @Autowired
    private EvaluateRepository evaluateRepository;
    @Autowired
    private HistoryRepository historyRepository;

    /**
     * 保存用户评价
     *
     * @param evaluate
     * @param bindingResult
     */
    @Override
    public Evaluate add(Evaluate evaluate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResultException(YoosureEnum.PUBLISH_ERR, bindingResult.getFieldError().getDefaultMessage());
        }
        Yoosure yoosure = yoosureRepository.findOne(evaluate.getYoosureId());
        History history = historyRepository.findByUserIdAndYoosureIdAndIsFinish(evaluate.getUserId(), evaluate.getYoosureId(), 1);
        Evaluate check = evaluateRepository.findByUserIdAndYoosureId(evaluate.getUserId(), evaluate.getYoosureId());
        //检查游学贴是否存在
        if (yoosure != null) {
            //检查游学贴是否结束
            if (yoosure.getIsFinish() != 1)
                throw new ResultException(YoosureEnum.NOT_FINISH);
            //检查用户是否在游学贴中
            if (history == null)
                throw new ResultException(UserEnum.USER_NOT_JOINED);
            //检查该用户是否已经评价
            if (check == null) {
                return save(evaluate);
            } else throw new ResultException(YoosureEnum.ONLY_EVALUATE_ONCE);
        } else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
    }

    @Override
    public Evaluate save(Evaluate evaluate) {
        return evaluateRepository.save(evaluate);
    }
}
