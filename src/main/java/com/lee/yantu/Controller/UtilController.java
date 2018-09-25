package com.lee.yantu.Controller;


import com.lee.yantu.Entity.SystemTag;
import com.lee.yantu.service.ImgService;
import com.lee.yantu.service.JournalService;
import com.lee.yantu.service.TagService;
import com.lee.yantu.VO.Result;
import com.lee.yantu.VO.TagVO;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.service.YoosureService;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/util")
public class UtilController {
    @Autowired
    private ImgService utilService;
    @Autowired
    private TagService tagService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private YoosureService yoosureService;

    /**
     * 上传图片，不适用于用户头像的上传
     *
     * @param img
     * @param flag 决定图片所属类别的标志,2为游学贴,3为手账
     * @param id   决定图片归属
     * @return
     */
    @PostMapping("/img/upload/fg-{flag}")
    public Result imgUpload(@RequestParam("file") MultipartFile img,
                            @PathVariable("flag") Integer flag,
                            @RequestParam("id") Integer id) {
        String imgName = utilService.uploadImg(img, id, flag);
        return ResultUtil.success(imgName);
    }

    /**
     * 点赞
     * @param key
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/{key}/agree/{id}")
    public Result agreeAdd(@PathVariable String key,
                           @PathVariable Integer id,
                           HttpServletRequest request) {
        if ("journal".equals(key)) {
            return ResultUtil.success(journalService.agreeAddOne(id,request));
        } else if ("yoosure".equals(key)) {
            return ResultUtil.success(yoosureService.agreeAddOne(id,request));
        } else throw new ResultException(SystemEnum.UNKNOWN_ERROR,"点赞失败");
    }


    /**
     * 获取指定标志的系统标签
     *
     * @param flag
     * @return
     */
    @GetMapping("/get/tags/fg-{flag}")
    public Result getAllSystemTag(@PathVariable("flag") Integer flag) {
        if (flag == 1 || flag == 2 || flag == 3) {
            List<SystemTag> tagList = tagService.findAllByTagFlag(flag);
            List<TagVO> tagVOList = new ArrayList<>();
            for (SystemTag tag : tagList) {
                TagVO tagVO = new TagVO();
                tagVO.setTagInfo(tag.getTagInfo());
                tagVO.setTagId(tag.getTagId());
                tagVOList.add(tagVO);
            }
            return ResultUtil.success(tagVOList);
        }
        throw new ResultException(SystemEnum.NO_THIS_FLAG);
    }
}
