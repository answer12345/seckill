package com.sangjie.seckill.vo;

public enum RespBeanEnum {
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    LOGIN_ERROR(50020, "密码或账号错误"),
    STOCK_ERROR(50021, "商品库存不足"),
    MUTTIBUY_ERROR(50022, "请勿重复购买"),
    REQUEST_LIMIT(50023, "请求次数过多");

    private final Integer code;

    private final String message;

    RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RespBeanEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
