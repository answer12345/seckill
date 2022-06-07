package com.sangjie.seckill.config;

import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.service.UserSerivce;
import com.sangjie.seckill.utils.CookieUtil;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class UserConfig implements HandlerMethodArgumentResolver {

    private UserSerivce userSerivce;

    public UserConfig(UserSerivce userSerivce) {
        this.userSerivce = userSerivce;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request =  webRequest.getNativeRequest(HttpServletRequest.class);
        String ticket = CookieUtil.getCookieValue(request, "userticket");
        if (ticket == null) {
            return null;
        }
        User user = userSerivce.getUserByCookie("user" + ticket);
        return user;
    }

}
