package com.lee.yantu.service;

import com.lee.yantu.Entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface BannerService {
    /**
     * 添加一个新的banner
     *
     * @param file
     * @param link
     * @return
     */
    Banner addBanner(MultipartFile file, String link, Integer isOnline);

    /**
     * 获取所有上线的banner
     *
     * @return
     */
    List<Banner> getOnlines();

    /**
     * 上线banner
     *
     * @param bannerId
     */
    Banner online(Integer bannerId);

    /**
     * 关闭banner
     *
     * @param bannerId
     */
    Banner downline(Integer bannerId);

    /**
     * 删除banner
     *
     * @param bannerId
     */
    void deleteBanner(Integer bannerId);

}
