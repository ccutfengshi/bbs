package com.ccut.bbs.controller;


import com.ccut.bbs.dto.PaginationDTO;
import com.ccut.bbs.mapper.UserMapper;
import com.ccut.bbs.model.User;
import com.ccut.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller  //@Controller可以暂时理解为允许该页面接收前端的请求
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,  //传入page。默认值为1，Integer类型
                        @RequestParam(name = "size",defaultValue = "5") Integer size  //传入页面长度 默认值为5

                        ) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)  //先检验index页面，检验完了再跳转index页面
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
