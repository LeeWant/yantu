package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.Identification;
import com.lee.yantu.Entity.User;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.repository.IdentifyRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.service.IdentifyService;
import com.lee.yantu.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentifyImpl implements IdentifyService {

    @Autowired
    IdentifyRepository identifyRepository;
    @Autowired
    UserRepository userRepository;
    //验证身份证
    @Override
    public boolean verify(Integer userId, String realName, String idCard, String school) {
        //检查用户是否存在
        User user = userRepository.findOne(userId);
        if(null == user || user.getIsDelete() == 1){
            throw new ResultException(UserEnum.USER_NOT_FOUND);
        }
        Identification identification;
        identification = identifyRepository.findByUserId(userId);
        //检查是否已验证
        if(identification == null){
            identification = new Identification();
        }else throw new ResultException(SystemEnum.VERIFIED);
        identification.setUserId(userId);
        identification.setRealName(realName);
        //验证身份证格式
        if(CheckUtil.checkId(idCard))
            identification.setIdCard(idCard);
        else throw new ResultException(SystemEnum.IDCARD_ERROR);
        identification.setSchool(school);
        identifyRepository.save(identification);
        return true;
    }
    //是否已验证
    @Override
    public boolean ifVerify(Integer userId) {
        Identification identification;
        identification = identifyRepository.findByUserId(userId);
        //检查是否已验证
        if(identification == null){
            return false;
        }else return true;
    }
    //获取验证信息
    @Override
    public Identification getOne(Integer userId) {
        return identifyRepository.findByUserId(userId);
    }
}
