package com.lee.yantu.service;


import com.lee.yantu.Entity.UserDefinedTag;

import java.util.List;

/**
 * @Author: Lee
 * @Description:
 * @Date: created in 16:31 2018/5/17
 */
public interface UserDefinedTagService {
    UserDefinedTag findOne(Integer tagId);

    UserDefinedTag save(UserDefinedTag tag);

    List<UserDefinedTag> findByUserId(Integer userId);

    void addUserDefinedTag(Integer userId, String tagInfo);
}
