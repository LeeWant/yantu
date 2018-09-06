package com.lee.yantu.util;

import com.lee.yantu.Entity.Tag;
import com.lee.yantu.Entity.User;
import com.lee.yantu.Entity.UserDefinedTag;
import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.VO.PageVO;
import com.lee.yantu.VO.TagVO;
import com.lee.yantu.VO.UserVO;
import com.lee.yantu.VO.YoosureSimpleVO;
import com.lee.yantu.repository.TagRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.service.SearchService;
import com.lee.yantu.service.TagService;
import com.lee.yantu.service.UserDefinedTagService;
import com.lee.yantu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class VOUtil {


    /**
     * 获取封装后的PageVO
     *
     * @param yoosures
     * @param tagService
     * @param userService
     * @return
     */
    public static PageVO getYoosurePageVO(Page<Yoosure> yoosures, TagService tagService, UserService userService) {
        //封装数据
        List<Tag> tags;
        List<YoosureSimpleVO> yoosureSimpleVOS = new ArrayList<>();
        for (Yoosure yoosure : yoosures) {
            //封装标签
            List<TagVO> tagVOS = new ArrayList<>();
            tags = tagService.findAllByYoosureId(yoosure.getYoosureId());
            for (Tag tag : tags) {
                TagVO tagVO = new TagVO();
                BeanUtils.copyProperties(tag, tagVO);
                tagVOS.add(tagVO);
            }
            YoosureSimpleVO yoosureSimpleVO = new YoosureSimpleVO();
            //封装user头像
            yoosureSimpleVO.setHeadImg(userService.findOne(yoosure.getUserId()).getHeadImg());
            //封装tagVOS
            yoosureSimpleVO.setTagVOS(tagVOS);
            //复制yoosure
            BeanUtils.copyProperties(yoosure, yoosureSimpleVO);
            //添加进yoosureSimpleVOS
            yoosureSimpleVOS.add(yoosureSimpleVO);
        }
        //创建返回PageVO
        PageVO<YoosureSimpleVO> pageVO = new PageVO<>();
        //复制page信息
        BeanUtils.copyProperties(yoosures, pageVO);
        pageVO.setVOS(yoosureSimpleVOS);
        return pageVO;
    }

    public static YoosureSimpleVO getYoosureSimpleVO(Yoosure yoosure, TagRepository tagRepository, UserRepository userRepository) {
        List<Tag> tags;
        //封装标签
        tags = tagRepository.findAllByYoosureId(yoosure.getYoosureId());
        List<TagVO> tagVOS = new ArrayList<>();
        for (Tag tag : tags) {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            tagVOS.add(tagVO);
        }
        YoosureSimpleVO yoosureSimpleVO = new YoosureSimpleVO();
        //封装user头像
        yoosureSimpleVO.setHeadImg(userRepository.findOne(yoosure.getUserId()).getHeadImg());
        //封装tagVOS
        yoosureSimpleVO.setTagVOS(tagVOS);
        //复制yoosure
        BeanUtils.copyProperties(yoosure, yoosureSimpleVO);
        //添加进yoosureSimpleVOS
        return yoosureSimpleVO;
    }


    public static PageVO getUserPageVO(Page<User> users, UserDefinedTagService udtService) {
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : users) {
            userVOS.add(getUserVO(user, udtService));
        }
        //创建返回PageVO
        PageVO<UserVO> pageVO = new PageVO<>();
        //复制page信息
        BeanUtils.copyProperties(users, pageVO);
        pageVO.setVOS(userVOS);
        return pageVO;
    }

    /**
     * 封装单个用户
     *
     * @param user
     * @param udtService
     * @return
     */
    public static UserVO getUserVO(User user, UserDefinedTagService udtService) {
        List<UserDefinedTag> tagList = udtService.findByUserId(user.getUserId());
        //定义标签返回值
        UserVO userVO = new UserVO();
        List<TagVO> tagVOS = new ArrayList<>();
        for (UserDefinedTag tag : tagList) {
            TagVO tagVO = new TagVO(tag.getTagInfo());
            tagVOS.add(tagVO);
        }
        //复制信息到VO
        BeanUtils.copyProperties(user, userVO);
        userVO.setTags(tagVOS);
        return userVO;
    }

    public static UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        //复制信息到VO
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
