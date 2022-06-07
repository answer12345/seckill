package com.sangjie.seckill.vo;

public class RespBean {
    private long code;
    private String message;
    private Object obj;

    public RespBean() {

    }

    public RespBean(long code, String message, Object obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "RespBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                '}';
    }

    public static RespBean returnSuccess() {
        return new RespBean(200, RespBeanEnum.SUCCESS.getMessage(), null);
    }

    public static RespBean returnSuccess(Object obj) {
        return new RespBean(200, RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    public static RespBean returnError(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    public static RespBean returnError(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
