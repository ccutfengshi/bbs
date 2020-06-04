package com.ccut.bbs.advice;

import com.ccut.bbs.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
//控制错误信息以及回写到error页面的内容
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {

        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else { //异常处理不了的时候默认显示内容
            model.addAttribute("message","服务冒烟了，要不然你稍后再试试！！！");
        }
        return new ModelAndView("error");
    }
}
