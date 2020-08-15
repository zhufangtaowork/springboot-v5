package com.example.springbootv5.common;

import lombok.Data;

import java.io.Serializable;


/**
 * 项目名称：springboot_demo
 * 类 名 称：Result
 * 类 描 述：结果类
 * 创建时间：2020/3/12 4:18 下午
 * 创 建 人：ZhuFangTao
 * @author fangtaozhu
 */
@Data
public class ResultView<T> extends ResultPageView implements Serializable{

    private static final long serialVersionUID = -496323121379560617L;

    private String code;
    private String msg;
    private T data;
    private String traceId;

    public ResultView setMsgCode(String code){
        this.code = code;
        this.msg = ResultCode.msg.get(code);
        return this;
    }
}
