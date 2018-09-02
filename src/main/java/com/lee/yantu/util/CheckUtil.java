package com.lee.yantu.util;

import java.util.regex.Pattern;

public class CheckUtil {
    /**
     * 检查邮箱格式是否正确
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        String regex = "\\w+\\@\\w+\\.[a-zA-Z]{2,4}";
        return Pattern.matches(regex, email);
    }

    /**
     * 检查昵称格式是否正确
     * @param nickName
     * @return
     */
    public static boolean checkNickName(String nickName){
        String regex = "[\\w\\u4e00-\\u9fa5]{2,20}";
        return Pattern.matches(regex, nickName);

    }

    /**
     * 检查密码格式是否正确
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
        String regex = "[\\w]{6,16}";
        return Pattern.matches(regex,password);
    }

    /**
     * 检查标签格式是否正确
     * @param tagInfo
     * @return
     */
    public static boolean checkTagInfo(String tagInfo){
        String regex = "[\\w\\u4e00-\\u9fa5]{1,10}";
        return Pattern.matches(regex,tagInfo);
    }

    /**
     * 身份证格式验证
     * @param id
     * @return
     */
    public static boolean checkId(String id) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!id.matches(reg)) {
            return false;
        }
        return true;
    }
}
