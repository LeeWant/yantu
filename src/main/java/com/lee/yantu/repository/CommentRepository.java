package com.lee.yantu.repository;

import com.lee.yantu.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByYoosureId(Integer yoosureId);

    List<Comment> findAllByUserId(Integer userId);

    List<Comment> findAllByJournalId(Integer journalId);

    Page<Comment> findAllByYoosureIdAndIsDelete(Pageable pageable, Integer yoosureId,Integer isDelete);

    Page<Comment> findAllByUserIdAndIsDelete(Pageable pageable, Integer userId,Integer isDelete);

    Page<Comment> findAllByJournalIdAndIsDelete(Pageable pageable, Integer journalId,Integer isDelete);

}

