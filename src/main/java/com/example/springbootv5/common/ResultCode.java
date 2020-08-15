package com.example.springbootv5.common;

import java.util.HashMap;

/**
 * 项目名称：springboot_demo
 * 类 名 称：ResultCode
 * 类 描 述：状态码
 * 创建时间：2020/3/12 4:44 下午
 * 创 建 人：ZhuFangTao
 *
 * @author fangtaozhu
 */
public class ResultCode {
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final HashMap<String, String> msg = new HashMap();
    /**
     * @Description：通用
     **/
    public static final String SUCCESS = "0000";
    public static final String FAIL = "9999";
    public static final String REQUIRED_PARAMETER_MISSING = "2002";
    public static final String UNKNOWN_ERROR = "-1";



    static {
        msg.put(SUCCESS,"操作正常!");
        msg.put(FAIL,"操作失败!");
        msg.put(REQUIRED_PARAMETER_MISSING,"缺少必要参数!");
        msg.put(UNKNOWN_ERROR,"未知错误,详情请查看日志!");
    }
}
