package com.lee.yantu.service;

import com.lee.yantu.Entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    void deleteComment(Integer id);

    Comment publishComment(Integer userId, Integer id, String content, Integer flag);

    Page<Comment> findAllById(Integer id, Integer flag,Integer pageNum);



}
