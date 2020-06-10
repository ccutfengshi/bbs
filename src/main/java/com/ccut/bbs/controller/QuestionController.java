package com.ccut.bbs.controller;

import com.ccut.bbs.dto.CommentDTO;
import com.ccut.bbs.dto.QuestionDTO;
import com.ccut.bbs.enums.CommentTypeEnum;
import com.ccut.bbs.service.CommentService;
import com.ccut.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model
                           ) {

        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> comments =  commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
