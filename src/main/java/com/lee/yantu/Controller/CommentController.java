package com.lee.yantu.Controller;


import com.lee.yantu.Entity.Comment;
import com.lee.yantu.Entity.User;
import com.lee.yantu.service.CommentService;
import com.lee.yantu.service.UserService;
import com.lee.yantu.VO.CommentVO;
import com.lee.yantu.VO.PageVO;
import com.lee.yantu.VO.Result;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;


    /**
     * 发表评论
     *
     * @param userId
     * @param content
     * @param id
     * @param flag
     * @return
     */
    @PostMapping("/publish/id-{id}&fg-{flag}")
    public Result publishComment(@RequestParam("userId") Integer userId,
                                 @RequestParam("content") String content,
                                 @PathVariable("id") Integer id,
                                 @PathVariable("flag") Integer flag) {
        if (userId != null && content != null
                && id != null && flag != null) {
            Comment comment = commentService.publishComment(userId, id, content, flag);
            User user = userService.findOne(userId);
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setNickName(user.getNickName());
            commentVO.setHeadImg(user.getHeadImg());
            return ResultUtil.success(commentVO);
        }throw new ResultException(SystemEnum.NULL_STRING);
    }

    /**
     * 分页获取所有评论
     *
     * @return
     */
    @GetMapping("/getAll/id-{id}&fg-{flag}&pg-{pageNum}")
    public Result getAllById(@PathVariable("id") Integer id,
                             @PathVariable("flag") Integer flag,
                             @PathVariable("pageNum") Integer pageNum) {
        //从数据库找出当前页数据
        Page<Comment> commentList = commentService.findAllById(id, flag, pageNum);
        List<CommentVO> commentVOList = new ArrayList<>();
        User user;
        //封装成List<CommentVO>
        for (Comment comment : commentList) {
            user = userService.findOne(comment.getUserId());
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setHeadImg(user.getHeadImg());
            commentVO.setNickName(user.getNickName());
            commentVOList.add(commentVO);
        }
        //进一步封装成pageVO
        PageVO pageVO = new PageVO();
        pageVO.setNumber(commentList.getNumber());
        pageVO.setSize(commentList.getSize());
        pageVO.setTotalPages(commentList.getTotalPages());
        pageVO.setNumberOfElements(commentList.getNumberOfElements());
        pageVO.setVOS(commentVOList);
        //返回数据
        return ResultUtil.success(pageVO);
    }


    @DeleteMapping("/delete/id-{commentId}")
    public Result deleteById(@PathVariable("commentId") Integer commentId) {
        commentService.deleteComment(commentId);
        return ResultUtil.success();
    }
}
