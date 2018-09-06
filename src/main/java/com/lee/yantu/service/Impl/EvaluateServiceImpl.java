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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Evaluate add(Evaluate evaluate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResultException(YoosureEnum.PUBLISH_ERR, bindingResult.getFieldError().getDefaultMessage());
        }
        User user = userRepository.findOne(evaluate.getUserId());
        Yoosure yoosure = yoosureRepository.findOne(evaluate.getYoosureId());
        History history = historyRepository.findByUserIdAndYoosureIdAndIsFinish(evaluate.getUserId(), evaluate.getYoosureId(), 1);
        //检查游学贴和用户是否存在
        if (yoosure != null && user != null) {
            //游学贴结束之后才能进行评价
            if (yoosure.getIsFinish() != 1) {
                throw new ResultException(YoosureEnum.NOT_FINISH);
            }
            //检查用户是否在游学贴中
            if (null != history && history.getOpFlag() == 1) {
                //检查该用户是否已经评价
                if (history.getEvaluateId() == 0) {
                    //保存评价
                    Evaluate evaluateRe = evaluateRepository.save(evaluate);
                    //保存历史纪录中的评价id
                    history.setEvaluateId(evaluateRe.getEvaluateId());
                    //更新历史纪录
                    historyRepository.save(history);
                    //完成一次评价信用分+50
                    user.setCreditScore(user.getCreditScore()+50);
                    userRepository.save(user);
                    return evaluateRe;
                } else throw new ResultException(YoosureEnum.ONLY_EVALUATE_ONCE);
            } else throw new ResultException(UserEnum.USER_NOT_JOINED);
        } else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
    }

    @Override
    public Evaluate getOne(Integer evaluateId) {
        return evaluateRepository.findOne(evaluateId);
    }

    @Override
    public Evaluate save(Evaluate evaluate) {
        return evaluateRepository.save(evaluate);
    }
}
