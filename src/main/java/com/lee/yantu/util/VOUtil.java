package com.lee.yantu.util;

import com.lee.yantu.Entity.*;
import com.lee.yantu.VO.*;
import com.lee.yantu.repository.CommentRepository;
import com.lee.yantu.repository.TagRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.service.SearchService;
import com.lee.yantu.service.TagService;
import com.lee.yantu.service.UserDefinedTagService;
import com.lee.yantu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.File;
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
            User user = userService.findOne(yoosure.getUserId());
            List<TagVO> tagVOS = new ArrayList<>();
            tags = tagService.findAllByYoosureId(yoosure.getYoosureId());
            for (Tag tag : tags) {
                TagVO tagVO = new TagVO();
                BeanUtils.copyProperties(tag, tagVO);
                tagVOS.add(tagVO);
            }
            YoosureSimpleVO yoosureSimpleVO = new YoosureSimpleVO();
            //封装user头像
            yoosureSimpleVO.setHeadImg(user.getHeadImg());
            yoosureSimpleVO.setNickName(user.getNickName());
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

    /**
     * 封装一个简单Yoosure视图
     *
     * @param yoosure
     * @param tagRepository
     * @param userRepository
     * @return
     */
    public static YoosureSimpleVO getYoosureSimpleVO(Yoosure yoosure, TagRepository tagRepository, UserRepository userRepository) {
        List<Tag> tags;
        User user = userRepository.findOne(yoosure.getUserId());
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
        yoosureSimpleVO.setHeadImg(user.getHeadImg());
        yoosureSimpleVO.setNickName(user.getNickName());
        //封装tagVOS
        yoosureSimpleVO.setTagVOS(tagVOS);
        //复制yoosure
        BeanUtils.copyProperties(yoosure, yoosureSimpleVO);
        //添加进yoosureSimpleVOS
        return yoosureSimpleVO;
    }

    /**
     * 封装一个journalVO
     *
     * @param journal
     * @param tagRepository
     * @param commentRepository
     * @param userRepository
     * @return
     */
    public static JournalVO getJournalVO(Journal journal, String htmlPath, TagRepository tagRepository, CommentRepository commentRepository, UserRepository userRepository) {
        User user = userRepository.findOne(journal.getUserId());
        JournalVO journalVO = new JournalVO();
        //封装标签
        List<TagVO> tagVOS = new ArrayList<>();
        List<Tag> tags = tagRepository.findAllByJournalId(journal.getJournalId());
        for (Tag tag : tags) {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            tagVOS.add(tagVO);
        }
        //封装VO
        BeanUtils.copyProperties(journal, journalVO);
        //评论数
        journalVO.setCommentNum(commentRepository.countCommentByJournalIdAndIsDelete(journal.getJournalId(), 0));
        journalVO.setTagVOS(tagVOS);
        journalVO.setUserName(user.getNickName());
        journalVO.setUrl(journal.getContent());
        journalVO.setFilePath(journalVO.getUrl().substring(0, 13));
        journalVO.setHeadImg(user.getHeadImg());
        //封装commentVO
        List<Comment> commentList = commentRepository.findAllByJournalIdAndIsDelete(journal.getJournalId(), 0);
        journalVO.setCommentVOS(getCommentVOS(commentList, userRepository));
        //修改路径，防止文件错误
        if (null != htmlPath && !"".equals(htmlPath))
            journalVO.setHtml(HtmlUtil.HtmlToString(htmlPath + File.separator + journalVO.getUrl()));
        return journalVO;
    }

    public static JournalVO getJournalVO(Journal journal, TagRepository tagRepository, CommentRepository commentRepository, UserRepository userRepository) {
        return getJournalVO(journal, "", tagRepository, commentRepository, userRepository);
    }

    /**
     * 获取一个CommentVOS
     *
     * @param comments
     * @return
     */
    public static List<CommentVO> getCommentVOS(List<Comment> comments, UserRepository userRepository) {
        List<CommentVO> commentVOS = new ArrayList<>();
        for (Comment comment :
                comments) {
            CommentVO commentVO = new CommentVO();
            User user = userRepository.findOne(comment.getUserId());
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setNickName(user.getNickName());
            commentVO.setHeadImg(user.getHeadImg());
            commentVOS.add(commentVO);
        }
        return commentVOS;
    }

    /**
     * 封装一个用户pageVO
     *
     * @param users
     * @param udtService
     * @return
     */
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
    //分页获取详细Journal信息
    public static PageVO<JournalVO> getJournalVOS(Page<Journal> journals,String htmlPath,TagRepository tagRepository,CommentRepository commentRepository,UserRepository userRepository){
        PageVO<JournalVO> journalVOPageVO = new PageVO<>();
        BeanUtils.copyProperties(journals,journalVOPageVO);
        List<JournalVO> journalVOS = new ArrayList<>();
        for(Journal journal:journals){
            JournalVO journalVO = VOUtil.getJournalVO(journal,htmlPath,tagRepository,commentRepository,userRepository);
            journalVOS.add(journalVO);
        }
        journalVOPageVO.setVOS(journalVOS);
        return journalVOPageVO;
    }


    /**
     * 获取Journals
     * @param journals
     * @param userRepository
     * @return
     */
    public static PageVO<JournalSimpleVO> getJournalSimpleVOS(Page<Journal> journals,UserRepository userRepository){
        PageVO<JournalSimpleVO> journalSimpleVOPageVO = new PageVO<>();
        BeanUtils.copyProperties(journals,journalSimpleVOPageVO);
        List<JournalSimpleVO> journalSimpleVOS = new ArrayList<>();
        for (Journal journal : journals){
            User user = userRepository.findOne(journal.getUserId());
            JournalSimpleVO journalSimpleVO = new JournalSimpleVO();
            BeanUtils.copyProperties(user,journalSimpleVO);
            BeanUtils.copyProperties(journal,journalSimpleVO);
            journalSimpleVOS.add(journalSimpleVO);
        }
        journalSimpleVOPageVO.setVOS(journalSimpleVOS);
        return journalSimpleVOPageVO;
    }
}
