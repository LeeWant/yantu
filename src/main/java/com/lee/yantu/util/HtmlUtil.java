package com.lee.yantu.util;

import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.exception.ResultException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * html文件解析类
 *
 * @Author: Lee
 * @Description:
 * @Date: created in 21:26 2018/9/17
 */
public class HtmlUtil {
    public static String HtmlToString(File file) {
        String html = "";
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            while (html != null) {
                html = bf.readLine();
                if (html == null) {
                    break;
                }
                sb.append(html);
            }
            bf.close();
        } catch (Exception e) {
            //抛出异常
            throw new ResultException(SystemEnum.UNKNOWN_ERROR, e.getMessage());
        }
        return sb.toString();

    }

    public static String HtmlToString(String path) {
        if (path != null && !"".equals(path)) {
            File file = new File(path);
            return HtmlToString(file);
        }else return "";
    }
}
