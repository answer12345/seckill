package com.sangjie.seckill.handler;

import com.sangjie.seckill.Exception.BuyException;
import com.sangjie.seckill.Exception.LoginException;
import com.sangjie.seckill.vo.RespBean;
import com.sangjie.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private RedisTemplate redisTemplate;

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public RespBean loginExceptionHandler(Exception e) {
        return RespBean.returnError(RespBeanEnum.LOGIN_ERROR);
    }

    @ExceptionHandler(BuyException.class)
    public void BuyException(Model model, Exception e) {
        redisTemplate.opsForValue().set(e.getMessage(), true);
    }

}
