package com.ccut.bbs.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 页码信息封装到这个对象里面
 * 这个对象包裹的就是页面包含的元素
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious; //是否有向前按钮
    private boolean showFirstPage; //是否有第一页按钮
    private boolean showNext; //是否有下一页
    private boolean showEndPage;  //展示最后一页
    private Integer page; //判断当前页是哪一页
    private List<Integer> pages = new ArrayList<>();  //页码数
    private Integer totalPage;

    /**
     * 计算有多少页可以显示
     */
    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = page;
        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i); //往前头部插入
            }

            if (page + i <= totalPage){
                pages.add(page + i);  //往后尾部插入
            }
        }


        //是否展示上一页
        if (page == 1) {
            showPrevious = false; //是第一页的时候 不显示上一页
        }else {
            showPrevious = true;
        }

        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        }else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        }else {
            showEndPage = true;
        }


    }
}
