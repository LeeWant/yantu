package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.*;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.repository.*;
import com.lee.yantu.service.YoosureService;
import com.lee.yantu.dto.SortDto;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.enums.YoosureEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

/**
 * @Author: Lee
 * @Description:
 * @Date: created in 22:28 2018/5/19
 */
@Service
public class YoosureServiceImpl implements YoosureService {
    @Autowired
    private YoosureRepository yoosureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private SystemTagRepository systemTagRepository;
    @Autowired
    private TagRepository tagRepository;

    /**
     * 保存yoosure
     *
     * @param yoosure
     * @return
     */
    @Override
    public Yoosure save(Yoosure yoosure) {
        return yoosureRepository.save(yoosure);
    }

    /**
     * 发布yoosure贴
     *
     * @param yoosure
     * @param bindingResult
     * @return
     */
    @Override
    @Transactional
    public Yoosure publishYoosure(Yoosure yoosure, BindingResult bindingResult, Integer[] tagIdArr) {
        User checkUser = userRepository.findOne(yoosure.getUserId());
        //该用户不存在
        if (null == checkUser) {
            throw new ResultException(UserEnum.USER_NOT_FOUND);
        }
        //检查用户是否加入,为0表示可以加入
        if (0 == checkUser.getJoinId()) {
            //检查传递过来的参数是否有问题
            if (bindingResult.hasErrors()) {
                throw new ResultException(YoosureEnum.PUBLISH_ERR, bindingResult.getFieldError().getDefaultMessage());
            }
            //游学贴必须提前两天发布
            if(yoosure.getGoDate().getTime() <= System.currentTimeMillis() + 24 * 60 * 1000 * 2){
                throw new ResultException(YoosureEnum.DATE_ERROR);
            }
            //保存游学贴到数据库
            Yoosure reYoosure = yoosureRepository.save(yoosure);
            //设置用户参与的游学id
            checkUser.setJoinId(reYoosure.getYoosureId());
            //保存用户信息
            userRepository.save(checkUser);
            //添加历史记录
            History history = new History();
            history.setUserId(checkUser.getUserId());
            history.setYoosureId(yoosure.getYoosureId());
            historyRepository.save(history);
            //判断是否有标签信息传入
            if (null != tagIdArr) {
                for (Integer tagId : tagIdArr) {
                    SystemTag systemTag = systemTagRepository.findOne(tagId);
                    if (systemTag.getTagFlag() == 2) {
                        Tag tag = new Tag();
                        tag.setFlag(2);
                        tag.setTagId(tagId);
                        tag.setYoosureId(reYoosure.getYoosureId());
                        tag.setTagInfo(systemTag.getTagInfo());
                        tagRepository.save(tag);
                    }
                }
            }
            return reYoosure;
        } else throw new ResultException(YoosureEnum.CAN_NOT_JOIN_TWO);
    }

    /**
     * 用户加入游学
     *
     * @param userId
     * @param yoosureId
     */
    @Override
    @Transactional
    public void join(Integer userId, Integer yoosureId) {
        User user = userRepository.findOne(userId);
        Yoosure yoosure = yoosureRepository.findOne(yoosureId);
        //判断用户是否存在
        if (null != user && user.getIsDelete() != 1) {
            //判断用户是否加入其他游学
            if (user.getJoinId() == 0) {
                //判断游学是否存在
                if (null != yoosure && yoosure.getIsDelete() != 1) {
                    //判断游学是否人数已满
                    if (yoosure.getJoinedNum() < yoosure.getPeopleNum()) {
                        //判断游学是否过期
                        if (yoosure.getGoDate().getTime() > new Date().getTime()) {
                            //判断游学能否加入
                            if (yoosure.getIsFinish() == 0 && yoosure.getIsJoin() == 1) {
                                //游学信息的修改
                                //参加人数加1
                                Integer joinedNum = yoosure.getJoinedNum() + 1;
                                if (joinedNum == yoosure.getPeopleNum()) {
                                    yoosure.setIsJoin(0);
                                }
                                yoosure.setJoinedNum(joinedNum);
                                yoosureRepository.save(yoosure);
                                //历史信息添加
                                History history = new History();
                                history.setUserId(userId);
                                history.setYoosureId(yoosureId);
                                historyRepository.save(history);
                                //保存用户信息
                                user.setJoinId(yoosureId);
                                userRepository.save(user);
                            } else throw new ResultException(YoosureEnum.CAN_NOT_JOIN);
                        } else throw new ResultException(YoosureEnum.YOOSURE_OVERDATE);
                    } else throw new ResultException(YoosureEnum.LIMIT_FULL);
                } else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
            } else throw new ResultException(YoosureEnum.CAN_NOT_JOIN_TWO);
        } else throw new ResultException(UserEnum.USER_NOT_FOUND);
    }

    /**
     * 删除指定id的游学贴
     *
     * @param yoosureId
     */
    @Override
    @Transactional
    public void deleteByYoosureId(Integer yoosureId) {
        Yoosure delYoosure = yoosureRepository.findOne(yoosureId);
        //判断游学贴是否不存在
        if (null != delYoosure && delYoosure.getIsDelete() != 1) {
            //修改游学贴状态
            delYoosure.setIsDelete(1);
            delYoosure.setIsJoin(0);
            yoosureRepository.save(delYoosure);
            //修改用户参与历史状态
            modifyHistoryFlag(yoosureId);
            //修改用户当前加入的游学id(置零)
            modifyUserJoinId(yoosureId);
        } else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
    }

    @Override
    public Yoosure findOne(Integer yoosureId) {
        return yoosureRepository.findOne(yoosureId);
    }

    /**
     * 分页获取yoosure
     *
     * @param page
     * @return
     */
    @Override
    public Page<Yoosure> getPage(Integer page) {
        Page<Yoosure> yoosures = yoosureRepository.findAllByIsFinishAndIsDelete(PageUtil.basicPage(page, 15, new SortDto("ASC", "goDate")), 0,0);
        return yoosures;
    }

    /**
     * 用户退出游学
     *
     * @param userId
     */
    @Override
    @Transactional
    public void quit(Integer userId) {
        User user = userRepository.findOne(userId);
        //检查用户是否存在并且加入了一个游学
        if (user != null && user.getIsDelete() != 1 && user.getJoinId() != 0) {
            Yoosure yoosure = yoosureRepository.findOne(user.getJoinId());
            History history = historyRepository.findByUserIdAndYoosureIdAndOpFlag(userId, yoosure.getYoosureId(), 1);
            //游学创建者无法退出
            if (user.getUserId() != yoosure.getUserId()) {
                //加入一个游学贴24小时之后才能退出
                if (history.getCreDate().getTime() + 24 * 60 * 60 * 1000 < System.currentTimeMillis()) {
                    //检查游学是否存在
                    if (yoosure != null && yoosure.getIsDelete() != 1) {
                        //判断游学是否可以加入或者退出
                        if (yoosure.getIsFinish() != 0) {
                            //修改用户joinId
                            user.setJoinId(0);
                            //修改yoosure人数
                            Integer joinNum = yoosure.getJoinedNum();
                            if (joinNum == yoosure.getPeopleNum()) {
                                yoosure.setIsJoin(1);
                            }
                            yoosure.setJoinedNum(joinNum - 1);
                            //修改历史纪录的flag
                            history.setOpFlag(0);
                            historyRepository.save(history);
                            //每次退出-10信用分
                            user.setCreditScore(user.getCreditScore()-10);
                            userRepository.save(user);
                            yoosureRepository.save(yoosure);
                        }
                        throw new ResultException(YoosureEnum.YOOSURE_IS_FINISH);
                    } else throw new ResultException(YoosureEnum.YOOSURE_NOT_EXIST);
                } else throw new ResultException(YoosureEnum.CAN_NOT_QUIT);
            } else throw new ResultException(YoosureEnum.YOU_CAN_NOT_QUIT);
        } else throw new ResultException(UserEnum.USER_NOT_FOUND);
    }

    /**
     * 结束游学
     *
     * @param yoosure
     */
    @Override
    @Transactional
    public void finish(Yoosure yoosure) {
        //游学开始24小时后才能关闭
        if (yoosure.getGoDate().getTime() + 24 * 60 * 60 * 1000 < System.currentTimeMillis()) {
            //结束游学
            yoosure.setIsFinish(1);
            List<History> histories = historyRepository.findAllByYoosureId(yoosure.getYoosureId());
            for (History history : histories) {
                history.setIsFinish(1);
                historyRepository.save(history);
            }
            modifyUserJoinId(yoosure.getYoosureId());
            yoosureRepository.save(yoosure);
        } else throw new ResultException(YoosureEnum.CAN_NOT_FINISH);
    }

    /**
     * 查找所有能够加入的游学
     *
     * @return
     */
    @Override
    public List<Yoosure> findAllCanJoin() {
        List<Yoosure> yoosures = yoosureRepository.findAllByIsDeleteAndIsJoinAndIsFinish(0, 1, 0);
        return yoosures;
    }

    @Override
    public List<Yoosure> findAllNotFinish() {
        List<Yoosure> yoosures = yoosureRepository.findAllByIsFinish(0);
        return yoosures;
    }

    /**
     * 通过yoosureId将参与该游学贴的用户的joinId设置为0
     *
     * @param yoosureId
     */
    private void modifyUserJoinId(Integer yoosureId) {
        List<User> userList = userRepository.findAllByJoinId(yoosureId);
        for (User user : userList) {
            user.setJoinId(0);
            userRepository.save(user);
        }

    }

    /**
     * 将历史记录中的flag修改为0表示用户退出
     *
     * @param yoosureId
     */
    private void modifyHistoryFlag(Integer yoosureId) {
        List<History> historyList = historyRepository.findAllByYoosureId(yoosureId);
        for (History history : historyList) {
            history.setOpFlag(0);
            historyRepository.save(history);
        }
    }


}
