package com.lee.yantu.Controller;


import com.lee.yantu.VO.Result;
import com.lee.yantu.service.Impl.JournalServiceImpl;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class JournalController {

    @Autowired
    JournalServiceImpl journalService;

    @PostMapping("/journal")
    public Result add(@RequestParam Integer userId,
                      @RequestParam String title,
                      @RequestParam MultipartFile html,
                      @RequestParam(required = false) MultipartFile[] img,
                      @RequestParam(required = false) Integer[] tagIdArr,
                      @RequestParam Integer isOpen){
        return ResultUtil.success(journalService.publishJournal(userId,title,html,img,tagIdArr,isOpen));
    }

    @GetMapping("/journal/{journalId}")
    public Result get(@PathVariable Integer journalId,
                      HttpServletRequest request){
        return ResultUtil.success(journalService.getOne(journalId,request));
    }

    @DeleteMapping("/journal/{journalId}")
    public Result delete(@PathVariable Integer journalId,HttpServletRequest request){
        journalService.delete(journalId,request);
        return ResultUtil.success();
    }

    @GetMapping("/journals/{userId}")
    public Result getAllByUserId(@PathVariable Integer userId,HttpServletRequest request){
        return ResultUtil.success(journalService.getAllByUserId(userId,request));
    }

    @GetMapping("/journal/changeStatus/{journalId}")
    public Result changeStatus(@PathVariable Integer journalId,HttpServletRequest request){
        return ResultUtil.success(journalService.changeStatus(journalId,request));
    }
}
