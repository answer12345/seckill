package com.sangjie.seckill.service;

import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserSerivce {
    RespBean doLogin(String mobile, String password, HttpServletRequest request, HttpServletResponse response);

    User getUserByCookie(String name);
}
