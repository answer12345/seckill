package com.sangjie.seckill.service.impl;

import com.sangjie.seckill.mapper.SeckillOrderMapper;
import com.sangjie.seckill.pojo.SeckillOrder;
import com.sangjie.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Long> getIdByUser(Long userId) {
        List<Long> res = seckillOrderMapper.getIdByUser(userId);
        return res;
    }

    @Override
    public int getResult(Long goodsId, Long userId) {
        SeckillOrder seckillOrder = seckillOrderMapper.getSeckillOrderByUserAndGoodsId(goodsId, userId);
        //System.out.println(goodsId);
        //System.out.println(userId);
        if (seckillOrder != null) {
            return 1;
        } else {
            //已经卖完
            if (redisTemplate.hasKey(goodsId + "IsEmpty")) {
                return -1;
            }
            //正在排队
            return 0;
        }
    }
}
