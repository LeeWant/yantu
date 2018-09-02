package com.lee.yantu.service;

import com.lee.yantu.Entity.Img;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgService {

    /**
     * 上传图片
     * @param img 图片
     * @param flag 类型
     * @return
     */
    String uploadImg(MultipartFile img,Integer id,Integer flag);

    List<Img> getImgById(Integer id, Integer flag);



}
