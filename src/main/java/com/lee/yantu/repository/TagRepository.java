package com.lee.yantu.repository;

import com.lee.yantu.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    List<Tag> findAllByYoosureId(Integer yoosureId);

    List<Tag> findAllByUserId(Integer userId);

    List<Tag> findAllByJournalId(Integer journalId);

    List<Tag> findAllByTagId(Integer tagId);


}
