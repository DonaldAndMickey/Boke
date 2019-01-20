package com.feifan.exception;

/**
 * 用户自定义异常
 * 
 * @author Donald
 * @date 2019/01/18
 * @see
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 771650899438110987L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
