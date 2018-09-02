package com.lee.yantu.service;

import com.lee.yantu.Entity.Identification;

public interface IdentifyService {
    boolean verify(Integer userId,String realName,String idCard,String school);
    boolean ifVerify(Integer userId);
    Identification getOne(Integer userId);
}
