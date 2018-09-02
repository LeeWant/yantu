package com.lee.yantu.Controller;

import com.lee.yantu.VO.Result;
import com.lee.yantu.service.IdentifyService;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identify")
public class IdentifyController {
    @Autowired
    IdentifyService identifyService;

    /**
     * 对一个用户信息进行验证
     * @param userId
     * @param realName
     * @param idCard
     * @param school
     * @return
     */
    @PostMapping("")
    public Result identification(@RequestParam("userId") Integer userId,
                                 @RequestParam("realName") String realName,
                                 @RequestParam("idCard") String idCard,
                                 @RequestParam("school") String school){
        identifyService.verify(userId,realName,idCard,school);
        return ResultUtil.success();
    }

    /**
     * 获取一个验证信息
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result getOne(@PathVariable("userId") Integer userId){
        return ResultUtil.success(identifyService.getOne(userId));
    }
}
