package spring17.dto;

import lombok.Data;
import spring17.exception.CustomizeErrorCode;
import spring17.exception.CustomizeException;

/**
 * Author:ShiQi
 * Date:2019/12/13-19:44
 */
@Data
public class ResultDTO {
    //基于什么类型显示
    private Integer code;
    //返回信息
    private String message;

    public static ResultDTO errorOf(Integer code,String message){
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

    public static ResultDTO okOf(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

}
