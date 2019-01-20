package com.feifan.exception;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.feifan.vo.ResultObject;

/**
 * 全局异常类
 * 
 * @author Donald
 * @date 2019/01/18
 * @see
 */
@RestControllerAdvice
@ResponseBody
public class GloableExecption {

    private static final Logger LOGGER = LoggerFactory.getLogger(GloableExecption.class);

    // shiro 认证异常
    @ExceptionHandler(ShiroException.class)
    public ResultObject throwShiroExce(ShiroException e) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getPrincipal();
        LOGGER.info("{} 用户访问失败，权限不够，{},请练习管理员申请对应的权限", username, e.getMessage(), e);
        return ResultObject.build(e.getMessage());
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResultObject throwUnAnon(UnauthenticatedException e) {
        LOGGER.info("本方法不允许匿名访问:", e);
        return ResultObject.build("本方法不支持匿名访问，请先登录");
    }

    // 自定义异常处理
    @ExceptionHandler(ServiceException.class)
    public ResultObject throwService(Exception e) {
        LOGGER.info("throwServiceException", e);
        return ResultObject.build(e.getMessage());

    }

    // 访问异常
    @ExceptionHandler(Exception.class)
    public ResultObject throwException(Exception e) {
        LOGGER.info("throwException", e);
        return ResultObject.build(e.getMessage());
    }

}
