package com.lee.yantu.Controller;


import com.lee.yantu.VO.Result;
import com.lee.yantu.service.Impl.JournalServiceImpl;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class JournalController {

    @Autowired
    JournalServiceImpl journalService;

    @PostMapping("/journal")
    public Result add(@RequestParam Integer userId,
                      @RequestParam String title,
                      @RequestParam MultipartFile html,
                      @RequestParam(required = false) MultipartFile img1,
                      @RequestParam(required = false) MultipartFile img2,
                      @RequestParam(required = false) MultipartFile img3,
                      @RequestParam(required = false) MultipartFile img4,
                      @RequestParam(required = false) MultipartFile img5,
                      @RequestParam(required = false) Integer[] tagIdArr,
                      @RequestParam Integer isOpen){
        List<MultipartFile> img = new ArrayList<>();
        if(img1 != null)
            img.add(img1);
        if(img2 != null)
            img.add(img2);
        if(img3 != null)
            img.add(img3);
        if(img4 != null)
            img.add(img4);
        if(img5 != null)
            img.add(img5);
        return ResultUtil.success(journalService.publishJournal(userId,title,html,img,tagIdArr,isOpen));
    }

    @PostMapping("journals")
    public Result getByPage(@RequestParam Integer page){
        return ResultUtil.success(journalService.getByPage(page));
    }

    @GetMapping("/journal/{journalId}")
    public Result getOne(@PathVariable Integer journalId,
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
