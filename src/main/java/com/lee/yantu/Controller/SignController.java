package com.lee.yantu.Controller;

import com.lee.yantu.Entity.User;
import com.lee.yantu.Entity.UserDefinedTag;
import com.lee.yantu.VO.Result;
import com.lee.yantu.VO.UserVO;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.service.UserDefinedTagService;
import com.lee.yantu.service.UserService;
import com.lee.yantu.util.JWT;
import com.lee.yantu.util.ResultUtil;
import com.lee.yantu.util.VOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SignController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDefinedTagService udtService;
    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    public Result register(User user) {
        User userBack = userService.register(user);
        String token = JWT.getTokenString(userBack);
        UserVO userVO = VOUtil.getUserVO(userBack);
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("user",userVO);
        return ResultUtil.success(map);
    }

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestParam("email") String email,
                        @RequestParam("password") String password) {
        User user = userService.findByEmail(email);
        if (null == user)
            throw new ResultException(UserEnum.USER_NOT_REGISTER);
        if (!password.equals(user.getPassword()))
            throw new ResultException(UserEnum.PASSWORD_ERR);
        String token = JWT.getTokenString(user);
        //封装返回数据
//        List<UserDefinedTag> tagList = udtService.findByUserId(user.getUserId());
        UserVO userVO = VOUtil.getUserVO(user,udtService);
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("user",userVO);
        return ResultUtil.success(map);
    }
}
