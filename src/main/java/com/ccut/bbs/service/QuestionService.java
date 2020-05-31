package com.ccut.bbs.service;

import com.ccut.bbs.dto.QuestionDTO;
import com.ccut.bbs.mapper.QuestionMapper;
import com.ccut.bbs.mapper.UserMapper;
import com.ccut.bbs.model.Question;
import com.ccut.bbs.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 当一个请求需要同时组装user和question的时候，就需要一个中间层service做这件事情
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> list = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : list) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //questionDTO.setId(question.getId());  这个方法是最古老的方法，推荐使用下面spring自带的方法
            BeanUtils.copyProperties(question,questionDTO); //这个工具类的作用是快速把question对象里所有的属性拷贝到questionDTO对象中
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
