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
     * 上传图片
     *
     * @param img
     * @param path
     * @return
     */
    public static String uploadImg(MultipartFile img, String path) {
        String oldFileName = img.getOriginalFilename();
        //检查是否是图片
        if (!checkImg(oldFileName, "jpg,gif,png,ico,bmp,jpeg"))
            throw new ResultException(SystemEnum.FILE_NOT_IMG);
        return uploadFile(img, path);
    }

    /**
     * 上传html
     *
     * @param html
     * @param path
     * @return
     */
    public static String uploadHtml(MultipartFile html, String path) {
        String oldFileName = html.getOriginalFilename();
        //检查是否是html
        if (!checkImg(oldFileName, "html"))
            throw new ResultException(SystemEnum.FILE_NOT_HTML);
        return uploadFile(html, path);
    }

    public static String uploadImgByOldName(MultipartFile file, String imgPath){
        String imgName = file.getOriginalFilename();
        if (null == file && file.isEmpty())
            throw new ResultException(SystemEnum.FILE_IS_EMPTY);
        if (file.getSize() > 10 * 1024 * 1024)
            throw new ResultException(SystemEnum.UPLOAD_TOO_BIG);
        try {
            File desFile = new File(imgPath + File.separator + imgName);
            //如果目录不存在则创建新目录
            if (!desFile.getParentFile().exists()) {
                desFile.getParentFile().mkdirs();
            }
            //将文件输出到指定位置
            file.transferTo(desFile);
            return imgName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResultException(SystemEnum.UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    public static boolean checkImg(String fileName, String suffixList) {
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 上传文件返回重新储存的新名字
     *
     * @param file
     * @param path
     * @return
     */
    private static String uploadFile(MultipartFile file, String path) {
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
            newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
            File desFile = new File(imgPath + File.separator + newFileName);
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
