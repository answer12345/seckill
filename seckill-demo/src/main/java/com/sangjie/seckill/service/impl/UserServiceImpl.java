package com.sangjie.seckill.service.impl;

import com.sangjie.seckill.Exception.LoginException;
import com.sangjie.seckill.mapper.UserMapper;
import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.service.UserSerivce;
import com.sangjie.seckill.utils.CookieUtil;
import com.sangjie.seckill.utils.MD5Util;
import com.sangjie.seckill.utils.UUIDUtil;
import com.sangjie.seckill.vo.RespBean;
import com.sangjie.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserSerivce {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(String mobile, String password, HttpServletRequest request, HttpServletResponse response) {
        if (mobile == null || password == null) {
            return RespBean.returnError(RespBeanEnum.LOGIN_ERROR);
        }
        User user = userMapper.selectUserById(mobile);
        if (user == null) {
            throw new LoginException("密码或手机号错误");
        }
        if (!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new LoginException("密码或手机号错误");
        }

        //生成cookie
        String ticket = UUIDUtil.uudi();
        CookieUtil.setCookie(request, response, "userticket", ticket);

        //request.getSession().setAttribute("user", user);
        redisTemplate.opsForValue().set("user" + ticket, user);
        redisTemplate.expire("user", 1000, TimeUnit.SECONDS);
        return RespBean.returnSuccess(ticket);
    }

    @Override
    public User getUserByCookie(String name) {
        return (User) redisTemplate.opsForValue().get(name);
    }
}
