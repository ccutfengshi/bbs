package com.ccut.bbs.controller;


import com.ccut.bbs.dto.PaginationDTO;
import com.ccut.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller  //@Controller可以暂时理解为允许该页面接收前端的请求
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,  //传入page。默认值为1，Integer类型
                        @RequestParam(name = "size",defaultValue = "5") Integer size  //传入页面长度 默认值为5

                        ) {

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
