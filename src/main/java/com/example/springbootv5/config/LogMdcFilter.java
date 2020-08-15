package com.example.springbootv5.config;
 
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @title 为logback日志增加traceId
 * @author Xingbz
 * @createDate 2019-4-12
 */
@WebFilter(urlPatterns = "/*", filterName = "logMdcFilter")
@Order(1)
public class LogMdcFilter implements Filter {
    private static final String UNIQUE_ID = "traceId";
 
    @Override
    public void init(FilterConfig filterConfig) {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean bInsertMDC = insertMDC();
        try {
            chain.doFilter(request, response);
        } finally {
            if(bInsertMDC) {
                MDC.remove(UNIQUE_ID);
            }
        }
    }
 
    @Override
    public void destroy() {
    }
 
    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }
}