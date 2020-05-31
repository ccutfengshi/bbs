package com.ccut.bbs.controller;


import com.ccut.bbs.dto.QuestionDTO;
import com.ccut.bbs.mapper.QuestionMapper;
import com.ccut.bbs.mapper.UserMapper;
import com.ccut.bbs.model.Question;
import com.ccut.bbs.model.User;
import com.ccut.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller  //@Controller可以暂时理解为允许该页面接收前端的请求
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model
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

        List<QuestionDTO> questionList = questionService.list();
        for (QuestionDTO questionDTO : questionList) {
            questionDTO.setDescription("reset");
        }
        model.addAttribute("questions",questionList);
        return "index";
    }

}
