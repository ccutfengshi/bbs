package com.ccut.bbs.dto;

import com.ccut.bbs.exception.CustomizeErrorCode;
import com.ccut.bbs.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;  //告诉前端需要用什么显示的（类似4xx这种状态码）
    private String message;  //是在页面发送请求时回写提示的

    public static ResultDTO errorOf(Integer code,String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }


}
