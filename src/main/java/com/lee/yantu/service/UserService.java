package com.lee.yantu.service;

import com.lee.yantu.Entity.User;
import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.VO.YoosureSimpleVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @Author: Lee
 * @Description:
 * @Date: created in 22:00 2018/5/14
 */
public interface UserService {
    User findOne(Integer id);

    User findByEmail(String email);

    User register(User user);

    User modifyInfo(User user, MultipartFile file);

    User modifyPassword(Integer userId,String oldPassword,String newPassword);

    List<YoosureSimpleVO> findMyYoosures(Integer userId);
}
