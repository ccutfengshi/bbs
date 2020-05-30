package com.ccut.bbs.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;  //防止用户暴增的时候数组越界，所以给long
    private String bio;
    private String avatar_url; //获取头像
}
