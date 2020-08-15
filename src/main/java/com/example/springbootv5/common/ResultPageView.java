package com.example.springbootv5.common;

import lombok.Data;

import java.util.List;

/**
 * @ClassName： ResultPageView
 * @Description： TODO
 * @Date： 2020/8/10 10:32 上午
 * @author： ZhuFangTao
 */
@Data
public  class ResultPageView<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private Integer maxPage;
}
