package com.xiaofei.li.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 14:52
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseResult {
    private Integer statusCode;
    private String message;
    private Object result;

    public static ResponseResult success(Object result){
        ResponseResult rs = new ResponseResult();
        rs.setStatusCode(200);
        rs.setMessage("success");
        rs.setResult(result);
        return rs;
    }

    public static ResponseResult success(){
        ResponseResult rs = new ResponseResult();
        rs.setStatusCode(200);
        rs.setMessage("success");
        return rs;
    }

    public static ResponseResult fail(String msg){
        ResponseResult rs = new ResponseResult();
        rs.setStatusCode(500);
        rs.setMessage(msg);
        return rs;
    }
}
