package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.Banner;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.repository.BannerRepository;
import com.lee.yantu.service.BannerService;
import com.lee.yantu.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Value("${my.bannerPath}")
    private String bannerPath;
    @Autowired
    BannerRepository bannerRepository;

    @Override
    public Banner addBanner(MultipartFile file, String link, Integer isOnline) {
        String imgName = UploadUtil.uploadImg(file, bannerPath);
        Banner banner = new Banner();
        banner.setImg(imgName);
        banner.setLink(link);
        banner.setIsOnline(isOnline);
        //保存并返回banner;
        return bannerRepository.save(banner);
    }

    @Override
    public List<Banner> getOnlines() {
        return bannerRepository.findByIsOnlineAndIsDelete(1, 0);
    }

    @Override
    public Banner online(Integer bannerId) {
        Banner banner = bannerRepository.findOne(bannerId);
        if (null != banner) {
            banner.setIsOnline(1);
            banner.setOpDate(new Date());
            return bannerRepository.save(banner);
        } else throw new ResultException(SystemEnum.NOT_FOUND);
    }

    @Override
    public Banner downline(Integer bannerId) {
        Banner banner = bannerRepository.findOne(bannerId);
        if (null != banner) {
            banner.setIsOnline(0);
            banner.setOpDate(new Date());
            return bannerRepository.save(banner);
        } else throw new ResultException(SystemEnum.NOT_FOUND);
    }

    @Override
    public void deleteBanner(Integer bannerId) {
        Banner banner = bannerRepository.findOne(bannerId);
        if (null != banner) {
            banner.setIsDelete(1);
            banner.setOpDate(new Date());
            bannerRepository.save(banner);
        } else throw new ResultException(SystemEnum.NOT_FOUND);
    }
}
