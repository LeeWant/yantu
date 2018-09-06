package com.lee.yantu.Controller;


import com.lee.yantu.VO.Result;
import com.lee.yantu.service.BannerService;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Lee
 * @Description:
 * @Date: created in 20:52 2018/9/6
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/add")
    public Result addBanner(@RequestParam("img") MultipartFile file,
                            @RequestParam("link") String link,
                            @RequestParam("isOnline") Integer isOnline) {
        return ResultUtil.success(bannerService.addBanner(file, link, isOnline));
    }

    @GetMapping("/getOnline")
    public Result getOnline() {
        return ResultUtil.success(bannerService.getOnlines());
    }

    @GetMapping("/online/{bannerId}")
    public Result online(@PathVariable("bannerId") Integer bannerId) {
        return ResultUtil.success(bannerService.online(bannerId));
    }

    @GetMapping("/downline/{bannerId}")
    public Result downline(@PathVariable("bannerId") Integer bannerId) {
        return ResultUtil.success(bannerService.downline(bannerId));
    }

    @DeleteMapping("/delete/{bannerId}")
    public Result deleteBanner(@PathVariable("bannerId") Integer bannerId) {
        bannerService.deleteBanner(bannerId);
        return ResultUtil.success();
    }


}
