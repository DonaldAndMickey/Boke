package com.feifan.configure;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.feifan.exception.GloableExecption;

/**
 * servlet 过滤器
 * 
 * @author Donald
 * @date 2019/01/19
 * @see
 */
@Component
public class MyMVCFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GloableExecption.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("自定义过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        LOGGER.info("自定义过器执行过滤");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        LOGGER.info("自定义过滤器正常销毁");
    }

}
