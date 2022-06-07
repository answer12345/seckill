package com.sangjie.seckill.controller;

import com.sangjie.seckill.service.UserSerivce;
import com.sangjie.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserSerivce userSerivce;

    @RequestMapping(value = "toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public RespBean doLogin(String mobile, String password, HttpServletRequest request, HttpServletResponse response) {
        return userSerivce.doLogin(mobile, password, request, response);
    }
}
