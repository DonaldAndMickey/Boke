package com.feifan.vo;

public class ResultObject {
    private int code = 200;
    private String msg = "OK";
    private Object data;

    private ResultObject() {}

    public static ResultObject build() {
        return build(null, null);
    }

    public static ResultObject build(String msg) {
        return build(null, msg);
    }

    public static ResultObject build(Object obj) {
        return build(obj, null);
    }

    public static ResultObject build(Object obj, String msg) {
        ResultObject resultObject = new ResultObject();
        if (obj != null) {
            resultObject.data = obj;
        }
        if (msg != null) {
            resultObject.msg = msg;
        }
        return resultObject;
    }

    public static ResultObject error() {
        return error(500, null, "错误,请联系管理员!");
    }

    public static ResultObject error(Exception e) {
        return error(500, e, null);
    }

    public static ResultObject error(String msg) {
        return error(500, null, msg);
    }

    public static ResultObject error(Integer code, String msg) {
        return error(code, null, msg);
    }

    public static ResultObject error(Integer code, Exception e) {
        return error(code, e, null);
    }

    public static ResultObject error(Exception e, String msg) {
        return error(500, e, msg);
    }

    public static ResultObject error(Integer code, Exception e, String msg) {
        ResultObject resultObject = new ResultObject();
        if (code != null) {
            resultObject.code = code;
        }
        if (e != null) {
            resultObject.msg = e.getMessage();
        }
        if (msg != null) {
            resultObject.msg = msg;
        }
        return resultObject;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultObject(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
