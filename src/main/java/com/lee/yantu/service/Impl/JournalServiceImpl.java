package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.*;
import com.lee.yantu.VO.JournalSimpleVO;
import com.lee.yantu.VO.JournalVO;
import com.lee.yantu.VO.PageVO;
import com.lee.yantu.VO.TagVO;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.repository.*;
import com.lee.yantu.service.JournalService;
import com.lee.yantu.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    JournalRepository journalRepository;
    @Autowired
    ImgRepository imgRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    SystemTagRepository systemTagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Value("${my.htmlPath}")
    private String htmlPath;

    @Override
    @Transactional
    public JournalVO publishJournal(Integer userId,
                                    String title,
                                    MultipartFile html,
                                    MultipartFile[] imgs,
                                    Integer[] tagIdArr,
                                    Integer isOpen) {
        User user = userRepository.findOne(userId);
        if (user == null)
            throw new ResultException(UserEnum.USER_NOT_EXIST);
        Journal journal = new Journal();
        //设置是否公开
        if (isOpen == 0)
            journal.setIsOpen(0);
        else journal.setIsOpen(1);
        journal.setUserId(userId);
        journal.setTitle(title);
        //获取当前时间
        journal.setCreDate(new Date(System.currentTimeMillis()));
        //新建文件的路径
        String htmlNewPath = htmlPath + File.separator + journal.getCreDate().getTime();
        //上传html
        journal.setContent(journal.getCreDate().getTime() + "/" + UploadUtil.uploadHtml(html, htmlNewPath));
        journal = journalRepository.save(journal);
        //上传tag标记(需要journalId)
        //判断是否有标签信息传入
        if (null != tagIdArr) {
            for (Integer tagId : tagIdArr) {
                SystemTag systemTag = systemTagRepository.findOne(tagId);
                if (systemTag.getTagFlag() == 3) {
                    Tag tag = new Tag();
                    //日记标签
                    tag.setFlag(3);
                    tag.setTagId(tagId);
                    tag.setJournalId(journal.getJournalId());
                    tag.setTagInfo(systemTag.getTagInfo());
                    tagRepository.save(tag);
                }
            }
        }
        //上传图片(需要journalId)
        if (null != imgs) {
            for (MultipartFile img : imgs) {
                imgRepository.save(new Img(journal.getJournalId(), null, UploadUtil.uploadImgByOldName(img, htmlNewPath), 3));
            }
        }
        JournalVO journalVO = VOUtil.getJournalVO(journal,htmlPath, tagRepository, commentRepository, userRepository);
        return journalVO;
    }

    @Override
    public void delete(Integer journalId, HttpServletRequest request) {
        Journal journal = journalRepository.findOne(journalId);
        //解密token
        Token token = JWT.getTokenInstance(request.getHeader("token"));
        if (journal.getUserId() != token.getUserId())
            throw new ResultException(SystemEnum.NOT_HAVE_AUTHORITY);
        if (journal == null || journal.getIsDelete() == 1)
            throw new ResultException(SystemEnum.NOT_FOUND);
        journal.setIsDelete(1);
        journalRepository.save(journal);
    }

    /**
     * 获取一个手账
     *
     * @param journalId
     * @param request
     * @return
     */
    @Override
    public JournalVO getOne(Integer journalId, HttpServletRequest request) {
        Journal journal = journalRepository.findOne(journalId);
        //解密token
        Token token = JWT.getTokenInstance(request.getHeader("token"));
        if (journal == null || journal.getIsDelete() == 1)
            throw new ResultException(SystemEnum.NOT_FOUND);
        if (journal.getIsOpen() == 1) {
            return VOUtil.getJournalVO(journal, htmlPath, tagRepository, commentRepository, userRepository);
        } else if (journal.getIsOpen() == 0 && token.getUserId() == journal.getUserId()) {
            return VOUtil.getJournalVO(journal, htmlPath, tagRepository, commentRepository, userRepository);
        } else throw new ResultException(SystemEnum.NOT_HAVE_AUTHORITY);
    }

    /**
     * 获取某个用户的所有手账
     *
     * @param userId
     * @return
     */
    @Override
    public List<JournalVO> getAllByUserId(Integer userId, HttpServletRequest request) {
        //解密token
        Token token = JWT.getTokenInstance(request.getHeader("token"));
        //权限不足
        if (token.getUserId() != userId)
            throw new ResultException(SystemEnum.NOT_HAVE_AUTHORITY);
        List<Journal> journals = journalRepository.findAllByUserIdAndIsDelete(userId, 0);
        List<JournalVO> journalVOS = new ArrayList<>();
        //封装JournalVO
        for (Journal journal : journals) {
            JournalVO journalVO = VOUtil.getJournalVO(journal,tagRepository, commentRepository, userRepository);
            journalVOS.add(journalVO);
        }
        return journalVOS;
    }

    /**
     * 改变手账状态
     *
     * @param journalId
     * @param request
     */
    @Override
    public JournalVO changeStatus(Integer journalId, HttpServletRequest request) {
        //解密token
        Token token = JWT.getTokenInstance(request.getHeader("token"));
        Journal journal = journalRepository.findOne(journalId);
        if (journal == null || journal.getIsDelete() == 1)
            throw new ResultException(SystemEnum.NOT_FOUND);
        if (journal.getUserId() != token.getUserId())
            throw new ResultException(SystemEnum.NOT_HAVE_AUTHORITY);
        if (journal.getIsOpen() == 1)
            journal.setIsOpen(0);
        else journal.setIsOpen(1);
        return VOUtil.getJournalVO(journalRepository.save(journal), tagRepository, commentRepository, userRepository);
    }

    @Override
    public PageVO<JournalSimpleVO> getByPage(Integer page) {
        Page<Journal> journalPage = journalRepository.findByIsOpenAndIsDelete(PageUtil.basicPage(page,15,"creDate"),1,0);
        return VOUtil.getJournalSimpleVOS(journalPage,userRepository);
    }
}
