package com.lee.yantu.VO;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {

    private List<T> VOS;
    //当前页元素个数
    private Integer numberOfElements;
    //每页的元素个数
    private Integer size;
    //总页数
    private Integer totalPages;
    //当前页
    private Integer number;

}
