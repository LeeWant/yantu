package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.SystemTag;
import com.lee.yantu.Entity.Tag;
import com.lee.yantu.repository.SystemTagRepository;
import com.lee.yantu.repository.TagRepository;
import com.lee.yantu.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    //标签细节的dao层
    @Autowired
    private TagRepository tagRepository;
    //系统标签的dao层
    @Autowired
    private SystemTagRepository systemTagRepository;

    /**
     * 查找该yoosure的标签
     * @param yoosureId
     * @return
     */
    @Override
    public List<Tag> findAllByYoosureId(Integer yoosureId) {
        return tagRepository.findAllByYoosureId(yoosureId);
    }

    /**
     * 查找该用户的称号(非标签)
     * @param userId
     * @return
     */
    @Override
    public List<Tag> findAllByUserId(Integer userId) {
        return tagRepository.findAllByUserId(userId);
    }

    /**
     * 查找journal的标签
     * @param journalId
     * @return
     */
    @Override
    public List<Tag> findAllByJournalId(Integer journalId) {
        return tagRepository.findAllByJournalId(journalId);
    }

    /**
     * 根据flag获取该flag下的标签
     * @param tagFlag
     * @return
     */
    @Override
    public List<SystemTag> findAllByTagFlag(Integer tagFlag) {
        return systemTagRepository.findAllByTagFlag(tagFlag);
    }
}
