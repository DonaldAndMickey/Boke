package com.feifan.configure;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.feifan.exception.GloableExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * MVC 配置类
 * 
 * @author Donald
 * @date 2019/01/19
 * @see
 */
@SpringBootConfiguration
public class MVCConfigure extends WebMvcConfigurationSupport implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GloableExecption.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        LOGGER.info("前置拦截器请求到达控制层之前");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        LOGGER.info("ModelView 返回控制层之前");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        LOGGER.info("返回response响应浏览器 之后");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    // 注册拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this).addPathPatterns("/service/**");
        super.addInterceptors(registry);
    }

    // 注册消息转换器 fastjson 转换器
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializeConfig(SerializeConfig.globalInstance);
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fConverter.setFastJsonConfig(config);

        List<MediaType> list = new ArrayList<>();
        list.add(new MediaType("text", "html", Charset.forName("utf-8")));
        list.add(new MediaType("application", "json", Charset.forName("utf-8")));
        fConverter.setSupportedMediaTypes(list);
        converters.add(fConverter);
        super.configureMessageConverters(converters);
    }

    // 配置ViewResolver 视图解析器
    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/", ".html");
        // super.configureViewResolvers(registry);
    }

    // 配置静态资源处理器
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry.addResourceHandler("/images/**").addResourceLocations("/");
        // super.addResourceHandlers(registry);
    }
}
