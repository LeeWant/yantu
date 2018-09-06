package com.lee.yantu.Controller;


import com.lee.yantu.Entity.Tag;
import com.lee.yantu.Entity.User;
import com.lee.yantu.Entity.UserDefinedTag;
import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.VO.*;
import com.lee.yantu.service.TagService;
import com.lee.yantu.service.UserDefinedTagService;
import com.lee.yantu.service.UserService;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.service.YoosureService;
import com.lee.yantu.util.JWT;
import com.lee.yantu.util.ResultUtil;
import com.lee.yantu.util.VOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Author: Lee
 * @Description:
 * @Date: created in 21:58 2018/5/18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDefinedTagService udtService;
    @Autowired
    private TagService tagService;

    /**
     * 获取一个用户VO信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/getOne/uid-{userId}")
    public Result getOne(@PathVariable("userId") Integer userId) {
        User user = userService.findOne(userId);
        return ResultUtil.success(VOUtil.getUserVO(user, udtService));
    }



    /**
     * 修改信息
     *
     * @param user
     * @param file
     * @return
     */
    @PostMapping(value = "/update")
    public Result update(User user,
                         @RequestParam("userId") Integer userId,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @RequestParam(value = "tagInfo", required = false) String tagInfo) {
        //添加用户自定义标签
        if (null != tagInfo) {
            udtService.addUserDefinedTag(userId, tagInfo);
        }
        //修改基本信息
        User modifyUser = userService.modifyInfo(user, file);
        //封装数据

        return ResultUtil.success(VOUtil.getUserVO(modifyUser, udtService));
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping(value = "/modify/password")
    public Result modifyPassword(@RequestParam("userId") Integer userId,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        //修改密码
        if (null != oldPassword
                && null != newPassword
                && !"".equals(oldPassword)
                && !"".equals(newPassword)) {
            userService.modifyPassword(userId, oldPassword, newPassword);
            return ResultUtil.success();
        } else {
            throw new ResultException(UserEnum.PASSWORD_MISSION);
        }
    }

    /**
     * 获取用户完成的所有yoosure
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/yoosures/uid-{userId}")
    public Result getYoosures(@PathVariable("userId") Integer userId) {
        return ResultUtil.success(userService.findMyYoosures(userId));
    }
}
