package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.Comment;
import com.lee.yantu.repository.CommentRepository;
import com.lee.yantu.repository.JournalRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.repository.YoosureRepository;
import com.lee.yantu.service.CommentService;
import com.lee.yantu.dto.SortDto;
import com.lee.yantu.enums.CommentEnum;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private YoosureRepository yoosureRepository;
    @Autowired
    private JournalRepository journalRepository;

    /**
     * 删除评论，只是改了isDelete标志
     *
     * @param id
     */
    @Override
    public void deleteComment(Integer id) {
        Comment comment = commentRepository.findOne(id);
        if (null != comment) {
            if (comment.getIsDelete() != 1) {
                comment.setIsDelete(1);
                commentRepository.save(comment);
            } else throw new ResultException(CommentEnum.COMMENT_IS_DELETE);
        } else throw new ResultException(CommentEnum.COMMENT_NOT_FOUND);
    }

    /**
     * 发布评论
     *
     * @param userId
     * @param id
     * @param content
     * @param flag
     * @return
     */
    @Override
    public Comment publishComment(Integer userId, Integer id, String content, Integer flag) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(userId);
        //该评论属于游学贴
        if (flag == 2) {
            if (null != yoosureRepository.findOne(id)) {
                comment.setYoosureId(id);
            } else throw new ResultException(SystemEnum.NOT_FOUND);

        } else if (flag == 3) {
            //该评论属于手账
            if (null != journalRepository.findOne(id)) {
                comment.setJournalId(id);
            } else throw new ResultException(SystemEnum.NOT_FOUND);
        } else throw new ResultException(SystemEnum.NO_THIS_FLAG);
        comment.setFlag(flag);
        comment.setCreDate(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 查找对应id flag的评论
     *
     * @param id
     * @param flag
     * @return
     */
    @Override
    public Page<Comment> findAllById(Integer id, Integer flag, Integer pageNum) {
        Pageable pageable = PageUtil.basicPage(pageNum, 10, new SortDto("ASC", "creDate"));
        //根据用户id查找评论
        if (flag == 1) {
            if (null != userRepository.findOne(id)) {
                return commentRepository.findAllByUserIdAndIsDelete(pageable, id, 0);
            } else throw new ResultException(SystemEnum.NOT_FOUND);
            //根据游学id查找评论
        } else if (flag == 2) {
            if (null != yoosureRepository.findOne(id)) {
                return commentRepository.findAllByYoosureIdAndIsDelete(pageable, id, 0);
            } else throw new ResultException(SystemEnum.NOT_FOUND);
            //根据手账id查找评论
        } else if (flag == 3) {
            if (null != journalRepository.findOne(id)) {
                return commentRepository.findAllByJournalIdAndIsDelete(pageable, id, 0);
            } else throw new ResultException(SystemEnum.NOT_FOUND);
            //出现意外的flag
        } else throw new ResultException(SystemEnum.NO_THIS_FLAG);
    }


}
