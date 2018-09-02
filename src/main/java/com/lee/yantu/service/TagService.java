package com.lee.yantu.service;

import com.lee.yantu.Entity.SystemTag;
import com.lee.yantu.Entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findAllByYoosureId(Integer yoosureId);

    List<Tag> findAllByUserId(Integer userId);

    List<Tag> findAllByJournalId(Integer journalId);

    List<SystemTag> findAllByTagFlag(Integer tagFlag);
}
