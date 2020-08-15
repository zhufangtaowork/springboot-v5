package com.example.springbootv5.config;

import com.example.springbootv5.common.ResultCode;
import com.example.springbootv5.common.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fangtaozhu
 */
@Slf4j
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice {
    /**
     * 特别说明： 可以配置指定的异常处理,这里处理所有
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultView errorHandler(HttpServletRequest request, Exception e) {
        log.error("异常信息：",e);
        ResultView view = new ResultView();
        view.setMsgCode(ResultCode.UNKNOWN_ERROR);
        view.setTraceId(MDC.get("tranceId"));
        return view;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResultView){
            ResultView resultView = (ResultView)o;
            resultView.setTraceId(MDC.get("traceId"));
            log.info("response={}", o.toString());
        }
        return o;
    }
}