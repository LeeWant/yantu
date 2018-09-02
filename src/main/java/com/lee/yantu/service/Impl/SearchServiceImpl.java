package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.User;
import com.lee.yantu.VO.UserVO;
import com.lee.yantu.dto.SortDto;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.repository.YoosureRepository;
import com.lee.yantu.service.SearchService;
import com.lee.yantu.util.CheckUtil;
import com.lee.yantu.util.PageUtil;
import com.lee.yantu.util.VOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    YoosureRepository yoosureRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Page search(String keyword, String s, Integer page) {
        if (s.trim().equals("title")) {
            return yoosureRepository.
                    findByTitleLikeAndIsDeleteAndIsJoinAndIsFinish(
                            PageUtil.basicPage(page, 15, new SortDto(
                                    "goDate"
                            )), "%" + keyword + "%", 0, 1, 0
                    );
        } else if (s.trim().equals("school")) {
            return yoosureRepository.
                    findByTargetSchoolLikeAndIsDeleteAndIsJoinAndIsFinish(
                            PageUtil.basicPage(page, 15, new SortDto(
                                    "goDate"
                            )), "%" + keyword + "%", 0, 1, 0
                    );
        } else if (s.trim().equals("place")) {
            return yoosureRepository.
                    findByPlaceLikeAndIsDeleteAndIsJoinAndIsFinish(
                            PageUtil.basicPage(page, 15, new SortDto(
                                    "goDate"
                            )), "%" + keyword + "%", 0, 1, 0
                    );
        } else if (s.trim().equals("after")) {
            Date date = new Date(Long.parseLong(keyword) - 1000);
            Page dateAfterPage = yoosureRepository
                    .findByGoDateAfterAndIsDeleteAndIsJoinAndIsFinish
                            (PageUtil.basicPage(page, 15, new SortDto("goDate")), date, 0, 1, 0);
            return dateAfterPage;
        } else if (s.trim().equals("before")) {
            Date date = new Date(Long.parseLong(keyword) + 1000);
            Page dateBeforePage = yoosureRepository
                    .findByGoDateBeforeAndIsDeleteAndIsJoinAndIsFinish
                            (PageUtil.basicPage(page, 15, new SortDto("goDate")), date, 0, 1, 0);
            return dateBeforePage;
        } else if (s.trim().equals("greater")) {
            return yoosureRepository.
                    findByCostGreaterThanEqualAndIsDeleteAndIsJoinAndIsFinish(
                            PageUtil.basicPage(page, 15, new SortDto(
                                    "goDate"
                            )), new BigDecimal(keyword.trim()), 0, 1, 0
                    );
        } else if (s.trim().equals("less")) {
            return yoosureRepository.
                    findByCostLessThanEqualAndIsDeleteAndIsJoinAndIsFinish(
                            PageUtil.basicPage(page, 15, new SortDto(
                                    "goDate"
                            )), new BigDecimal(keyword.trim()), 0, 1, 0
                    );
        } else throw new ResultException(SystemEnum.KEYWORD_ERROR);
    }


    @Override
    public Page searchUserByUserName(String name, Integer page) {
        Page users = userRepository.findByNickNameLikeAndIsDelete(
                PageUtil.basicPage(page, 15, new SortDto("nickName")), "%" + name + "%", 0);

        return users;
    }

    @Override
    public User searchUserByUserEmail(String email) {
            System.out.println(email);
        if (CheckUtil.isEmail(email)) {
            User user = userRepository.findByEmail(email);
            return user;
        }else throw new ResultException(UserEnum.EMAIL_FORMAT_ERR);
    }


}
