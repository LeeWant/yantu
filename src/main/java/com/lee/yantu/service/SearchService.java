package com.lee.yantu.service;

import com.lee.yantu.Entity.User;
import org.springframework.data.domain.Page;

public interface SearchService {

    /**
     * 搜索游学贴通过关键字
     * @param keyword 关键字
     * @param s 为搜索指定类型
     * @param page 页码默认为0
     * @return
     */
    Page search(String keyword,String s,Integer page);


    /**
     * 搜索用户通过用户昵称
     * @param name
     * @return
     */
    Page searchUserByUserName(String name,Integer page);

    /**
     * 搜索用户通过邮箱
     * @param email
     * @return
     */
    User searchUserByUserEmail(String email);

}
