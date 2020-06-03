package com.ccut.bbs.service;

import com.ccut.bbs.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount = questionMapper.count(); //页面所有的数量

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        //接下来的两个if是判断，如果当page=4或小于1时的容错处理
        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        //size*(page-1) 分页的公式
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //questionDTO.setId(question.getId());  这个方法是最古老的方法，推荐使用下面spring自带的方法
            BeanUtils.copyProperties(question,questionDTO); //这个工具类的作用是快速把question对象里所有的属性拷贝到questionDTO对象中
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList); //既然用到paginationDTO，就要先把questionDTOList存进去

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId); //页面所有的数量

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        //接下来的两个if是判断，如果当page=4或小于1时的容错处理
        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page); //把页面需要展示的元素算出来

        //size*(page-1) 分页的公式
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //questionDTO.setId(question.getId());  这个方法是最古老的方法，推荐使用下面spring自带的方法
            BeanUtils.copyProperties(question,questionDTO); //这个工具类的作用是快速把question对象里所有的属性拷贝到questionDTO对象中
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList); //既然用到paginationDTO，就要先把questionDTOList存进去
        return paginationDTO;
    }
}
