package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.History;
import com.lee.yantu.Entity.User;
import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.repository.EvaluateRepository;
import com.lee.yantu.repository.HistoryRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.repository.YoosureRepository;
import com.lee.yantu.service.UserService;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.CheckUtil;
import com.lee.yantu.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Lee
 * @Description:
 * @Date: created in 21:58 2018/5/18
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${my.headPath}")
    private String headPath;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EvaluateRepository evaluateRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private YoosureRepository yoosureRepository;

    @Override
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**
     * 修改信息
     *
     * @param user
     * @param file
     * @return
     */
    @Override
    public User modifyInfo(User user, MultipartFile file) {
        //更新头像
        if (null != file) {
            user.setHeadImg(UploadUtil.uploadFile(file, headPath));
        }
        //修改基本信息
        User newUser = updateBaseInfo(user);
        return newUser;

    }

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public User modifyPassword(Integer userId, String oldPassword, String newPassword) {
        User checkUser = userRepository.findOne(userId);
        //检查密码格式是否正确
        if (!CheckUtil.checkPassword(newPassword)) {
            throw new ResultException(UserEnum.PASSWORD_FORMAT_ERR);
        }
        //检查用户是否存在
        if (null == checkUser) {
            throw new ResultException(UserEnum.USER_NOT_FOUND);
        }
        //检查密码是否正确
        if (oldPassword.equals(checkUser.getPassword())) {
            checkUser.setPassword(newPassword);
            return userRepository.save(checkUser);
        } else throw new ResultException(UserEnum.ORIGINAL_PASSWORD_ERR);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public User register(User user) {
        User cUser = userRepository.findByEmail(user.getEmail());
        //参数检查
        if (user.getNickName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new ResultException(UserEnum.LACK_PARAMETER);
        }
        //检查邮箱格式
        if (!CheckUtil.isEmail(user.getEmail())) {
            throw new ResultException(UserEnum.EMAIL_FORMAT_ERR);
        }
        //检查是否已被注册
        if (null != cUser) {
            throw new ResultException(UserEnum.USER_EXIST);
        }
        //检查密码格式
        if (!CheckUtil.checkPassword(user.getPassword())) {
            throw new ResultException(UserEnum.PASSWORD_FORMAT_ERR);
        }
        //检查昵称格式是否正确
        if (!CheckUtil.checkNickName(user.getNickName())) {
            throw new ResultException(UserEnum.NICKNAME_FORMAT_ERR);
        }
        //检查昵称是否重复
        if (null != userRepository.findByNickName(user.getNickName())) {
            throw new ResultException(UserEnum.NICKNAME_EXIST);
        }
        return userRepository.save(user);
    }


    /**
     * 更新基础信息,昵称，生日，头像路径，个性签名，自我简介，学校
     *
     * @param user
     * @return
     */
    private User updateBaseInfo(User user) {
        if (null != user) {
            User checkUser = userRepository.findOne(user.getUserId());
            if (null != user.getNickName()) {
                //检查昵称是否符合标准
                if (CheckUtil.checkNickName(user.getNickName())) {
                    checkUser.setNickName(user.getNickName());
                } else throw new ResultException(UserEnum.NICKNAME_FORMAT_ERR);
            }
            if (null != user.getBirthDate()) {
                checkUser.setBirthDate(user.getBirthDate());
            }
            if (null != user.getHeadImg()) {
                checkUser.setHeadImg(user.getHeadImg());
            }
            if (null != user.getSign()) {
                checkUser.setSign(user.getSign());
            }
            if (null != user.getDescription()) {
                checkUser.setDescription(user.getDescription());
            }
            if (null != user.getSchool()) {
                checkUser.setSchool(user.getSchool());
            }
            return userRepository.save(checkUser);
        } else throw new ResultException(UserEnum.UPDATE_MISTAKE);
    }



    /**
     * 查找用户完成的yoosure
     *
     * @param userId
     * @return
     */
    @Override
    public List<Yoosure> findMyYoosures(Integer userId) {
        List<History> histories = historyRepository.findAllByUserIdAndIsFinish(userId, 1);
        List<Yoosure> yoosures = new ArrayList<>();
        for(History history : histories){
            Yoosure yoosure = yoosureRepository.findOne(history.getYoosureId());
            yoosures.add(yoosure);
        }
        return yoosures;
    }
}
