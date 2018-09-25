package com.lee.yantu.Controller;


import com.lee.yantu.Entity.*;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.service.*;
import com.lee.yantu.VO.*;
import com.lee.yantu.enums.YoosureEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.ResultUtil;
import com.lee.yantu.util.VOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/yoosure")
public class YoosureController {
    @Autowired
    private YoosureService yoosureService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private CommentService commentService;

    /**
     * 发布游学
     *
     * @param yoosure
     * @param bindingResult
     * @param tagIdArr
     * @return
     */
    @PostMapping("/publish")
    public Result publishYoosure(@Valid Yoosure yoosure,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "tagIdArr", required = false) Integer[] tagIdArr
    ) {

        Yoosure reYoosure = yoosureService.publishYoosure(yoosure, bindingResult, tagIdArr);
        //封装数据
        YoosureSimpleVO yoosureSimpleVO = new YoosureSimpleVO();
        BeanUtils.copyProperties(reYoosure, yoosureSimpleVO);
        List<Tag> tags = tagService.findAllByYoosureId(reYoosure.getYoosureId());
        List<TagVO> tagVOS = new ArrayList<>();
        for (Tag tag : tags) {
            tagVOS.add(new TagVO(tag.getTagInfo(), tag.getTagId()));
        }
        yoosureSimpleVO.setTagVOS(tagVOS);
        return ResultUtil.success(yoosureSimpleVO);
    }

    /**
     * 删除游学
     *
     * @param yoosureId
     * @return
     */
    @DeleteMapping("/delete/id-{yoosureId}")
    public Result deleteById(@PathVariable("yoosureId") Integer yoosureId) {
        yoosureService.deleteByYoosureId(yoosureId);
        return ResultUtil.success();
    }

    /**
     * 用户加入游学
     *
     * @param userId
     * @param yoosureId
     * @return
     */
    @GetMapping("/join/uid-{userId}&yid-{yoosureId}")
    public Result join(@PathVariable("userId") Integer userId,
                       @PathVariable("yoosureId") Integer yoosureId) {
        if (userId != null && yoosureId != null) {
            yoosureService.join(userId, yoosureId);
        } else throw new ResultException(SystemEnum.NULL_STRING);
        return ResultUtil.success();
    }

    /**
     * 用户退出游学
     *
     * @param userId
     * @return
     */
    @PostMapping("/out")
    public Result out(@RequestParam(value = "userId") Integer userId) {
        if (userId != null) {
            yoosureService.quit(userId);
        } else throw new ResultException(SystemEnum.NULL_STRING);
        return ResultUtil.success();
    }

    /**
     * 创建者终结游学贴(必须要在游学关闭后才能结束)
     *
     * @param userId
     * @param yoosureId
     * @return
     */
    @PostMapping("/finish")
    public Result finish(@RequestParam("userId") Integer userId,
                         @RequestParam("yoosureId") Integer yoosureId) {
        Yoosure yoosure = yoosureService.findOne(yoosureId);
        if (yoosure != null) {
            if (userId == yoosure.getUserId()) {
                yoosureService.finish(yoosure);
                return ResultUtil.success();
            }else throw new ResultException(YoosureEnum.NOT_CREATOR);
        }else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
    }

    /**
     * 获取指定id的游学贴
     *
     * @param id
     * @return
     */
    @GetMapping("/get/id-{yoosureId}")
    public Result getOneById(@PathVariable("yoosureId") Integer id) {
        if (id != null) {
            Yoosure yoosure = yoosureService.findOne(id);
            if (null != yoosure) {
                if (yoosure.getIsDelete() != 1) {
                    //封装TagVO
                    List<Tag> tagList = tagService.findAllByYoosureId(id);
                    List<TagVO> tagVOList = new ArrayList<>();
                    for (Tag tag : tagList) {
                        TagVO tagVO = new TagVO(tag.getTagInfo(), tag.getTagId());
                        tagVOList.add(tagVO);
                    }
                    //封装CommentVO
                    List<CommentVO> commentVOList = new ArrayList<>();
                    Page<Comment> commentList = commentService.findAllById(id, 2, 0);
                    for (Comment comment : commentList) {
                        CommentVO commentVO = new CommentVO();
                        User user = userService.findOne(comment.getUserId());
                        BeanUtils.copyProperties(comment, commentVO);
                        commentVO.setNickName(user.getNickName());
                        commentVO.setHeadImg(user.getHeadImg());
                        commentVOList.add(commentVO);
                    }
                    //获取imgName
                    List<Img> imgList = imgService.getImgById(id, 2);
                    List<String> imgNames = new ArrayList<>();
                    for (Img img : imgList) {
                        imgNames.add(img.getImgPath());
                    }
                    //封装YoosureVO
                    User user = userService.findOne(yoosure.getUserId());
                    YoosureVO yoosureVO = new YoosureVO();
                    BeanUtils.copyProperties(yoosure, yoosureVO);
                    yoosureVO.setHeadImg(user.getHeadImg());
                    yoosureVO.setNickName(user.getNickName());
                    yoosureVO.setImgName(imgNames);
                    yoosureVO.setTagVOS(tagVOList);
                    yoosureVO.setCommentVOS(commentVOList);
                    return ResultUtil.success(yoosureVO);
                } else throw new ResultException(YoosureEnum.YOOSURE_IS_DELETE);
            } else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
        } else throw new ResultException(SystemEnum.NULL_STRING);
    }

    /**
     * 分页获取游学贴
     *
     * @param page
     * @return
     */
    @GetMapping("/getAll/pg-{page}")
    public Result getAll(@PathVariable Integer page) {
        //封装数据
        Page<Yoosure> yoosures = yoosureService.getPage(page);
        return ResultUtil.success(VOUtil.getYoosurePageVO(yoosures,tagService,userService));
    }

    @GetMapping("/getAllByAgreeNum/pg-{page}")
    public Result getAllByAgreeNum(@PathVariable Integer page){
        Page<Yoosure> yoosures = yoosureService.getPageByAgreeNum(page);
        return ResultUtil.success(VOUtil.getYoosurePageVO(yoosures,tagService,userService));
    }



}
