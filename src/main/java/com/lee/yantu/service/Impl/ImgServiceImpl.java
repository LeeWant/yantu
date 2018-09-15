package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.Img;
import com.lee.yantu.repository.ImgRepository;
import com.lee.yantu.service.ImgService;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImgServiceImpl implements ImgService {
    @Value("${my.imgPath}")
    private String path;
    @Autowired
    private ImgRepository imgRepository;

    @Override
    public String uploadImg(MultipartFile img, Integer id, Integer flag) {
        Img newImg = new Img();
        //判断属于哪个类型
        if (flag == 2) {
            if (imgRepository.findAllByYoosureId(id).size() > 4) {
                throw new ResultException(SystemEnum.CAN_NOT_UPLOAD);
            }
            newImg.setYoosureId(id);
            newImg.setFlag(2);
            newImg.setImgPath(UploadUtil.uploadImg(img, path));
            imgRepository.save(newImg);
        } else if (flag == 3) {
            if (imgRepository.findAllByYoosureId(id).size() > 4) {
                throw new ResultException(SystemEnum.CAN_NOT_UPLOAD);
            }
            newImg.setJournalId(id);
            newImg.setFlag(3);
            newImg.setImgPath(UploadUtil.uploadImg(img, path));
            imgRepository.save(newImg);
        } else throw new ResultException(SystemEnum.NO_THIS_FLAG);
        //返回图片名称
        return newImg.getImgPath();
    }

    /**
     * 获取指定id下的所有图片
     * @param id
     * @param flag
     * @return
     */
    @Override
    public List<Img> getImgById(Integer id, Integer flag) {
        if(flag == 2)
            return imgRepository.findAllByYoosureId(id);
        else if(flag == 3)
            return imgRepository.findAllByJournalId(id);
        else throw new ResultException(SystemEnum.NO_THIS_FLAG);
    }
}
