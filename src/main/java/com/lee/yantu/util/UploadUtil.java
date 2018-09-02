package com.lee.yantu.util;

import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadUtil {

    /**
     * 上传文件返回文件路径
     *
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file, String path) {
        //新的文件名
        String imgPath = path;
        String newFileName;
        String oldFileName = file.getOriginalFilename();
        if (null == file && file.isEmpty())
            throw new ResultException(SystemEnum.FILE_IS_EMPTY);
        if (file.getSize() > 10 * 1024 * 1024)
            throw new ResultException(SystemEnum.UPLOAD_TOO_BIG);
        try {
            //为图片重命名
            newFileName = UUID.randomUUID()+oldFileName.substring(oldFileName.lastIndexOf("."));
            File desFile = new File(imgPath + newFileName);
            //如果目录不存在则创建新目录
            if (!desFile.getParentFile().exists()) {
                desFile.getParentFile().mkdirs();
            }
            //将文件输出到指定位置
            file.transferTo(desFile);
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResultException(SystemEnum.UNKNOWN_ERROR, e.getMessage());
        }
    }


}
