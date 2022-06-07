package com.sangjie.seckill.service.impl;

import com.sangjie.seckill.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
/*
    //@Autowired
    //private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }*/
}
