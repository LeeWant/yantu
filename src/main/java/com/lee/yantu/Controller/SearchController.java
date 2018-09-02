package com.lee.yantu.Controller;

import com.lee.yantu.Entity.User;
import com.lee.yantu.VO.PageVO;
import com.lee.yantu.VO.Result;
import com.lee.yantu.VO.UserVO;
import com.lee.yantu.service.SearchService;
import com.lee.yantu.service.TagService;
import com.lee.yantu.service.UserDefinedTagService;
import com.lee.yantu.service.UserService;
import com.lee.yantu.util.CheckUtil;
import com.lee.yantu.util.ResultUtil;
import com.lee.yantu.util.VOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    TagService tagService;
    @Autowired
    UserService userService;
    @Autowired
    UserDefinedTagService udtService;

    @PostMapping("/yoosure/{key}/{page}")
    public Result searchYoo(@RequestParam("keyword") String keyword,
                            @PathVariable("key") String key,
                            @PathVariable("page") Integer page) {
        Page yoosures = searchService.search(keyword, key, page);
        //封装数据
        PageVO yoosurePage = VOUtil.getYoosurePageVO(yoosures, tagService, userService);
        return ResultUtil.success(yoosurePage);
    }

    @PostMapping("/user/nickName/{page}")
    public Result searchUser(@RequestParam("nickName") String nickName,
                             @PathVariable("page") Integer page) {
        Page users = searchService.searchUserByUserName(nickName, page);
        PageVO pageVO = VOUtil.getUserPageVO(users, udtService);
        return ResultUtil.success(pageVO);
    }

    @PostMapping("/user/email")
    public Result searchUserByEmail(@RequestParam("email") String email) {
        User user = searchService.searchUserByUserEmail(email);
        if (null != user) {
            UserVO userVO = VOUtil.getUserVO(user, udtService);
            return ResultUtil.success(userVO);
        }else return ResultUtil.success();
    }
}
