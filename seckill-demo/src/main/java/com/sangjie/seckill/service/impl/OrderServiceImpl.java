package com.sangjie.seckill.service.impl;

import com.sangjie.seckill.Exception.BuyException;
import com.sangjie.seckill.mapper.GoodsMapper;
import com.sangjie.seckill.mapper.OrderMapper;
import com.sangjie.seckill.mapper.SeckillGoodsMapper;
import com.sangjie.seckill.mapper.SeckillOrderMapper;
import com.sangjie.seckill.pojo.GoodsSeckillVo;
import com.sangjie.seckill.pojo.Order;
import com.sangjie.seckill.pojo.SeckillOrder;
import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public Order updateStockAndGetOrder(GoodsSeckillVo goodsSeckillVo, User user) {
        //GoodsSeckillVo goodsSeckillVo = goodsMapper.getGoodsVoById(goodsId);
        int stockNum = goodsSeckillVo.getStockCount();
        //判断库存是否为0
        Long goodsId = goodsSeckillVo.getId();
        if (stockNum < 1) {
            redisTemplate.opsForValue().set(goodsId+"IsEmpty", true);
            return null;
        }

        //判断是否重复购买
        Long userId = user.getId();
        List<Long> goodsIds = seckillOrderMapper.getIdByUser(userId);
        for (Long each : goodsIds) {
            if (each == goodsId) {
                return null;
            }
        }

        int res = seckillGoodsMapper.updateStock(goodsSeckillVo.getId());
        if (res != 1) {
            redisTemplate.opsForValue().set(goodsId+"IsEmpty", true);
            return null;
        }
        SeckillOrder seckillOrder = new SeckillOrder();
        Order order = new Order();
        order.setGoodsId(goodsSeckillVo.getId());
        order.setCreateDate(new Date());
        order.setGoodsName(goodsSeckillVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goodsSeckillVo.getSeckillPrice());
        order.setUserId(user.getId());
        order.setStatus(false);
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goodsSeckillVo.getId());
        int res2 = orderMapper.create(order);
        if (res2 != 1) {
            redisTemplate.opsForValue().set(goodsId+"IsEmpty", true);
            return null;
        }
        int res3 = seckillOrderMapper.create(seckillOrder);
        if (res3 != 1) {
            redisTemplate.opsForValue().set(goodsId+"IsEmpty", true);
            return null;
        }
        redisTemplate.opsForValue().set("seckillOrder" + goodsSeckillVo.getId() + user.getId(), order, 20, TimeUnit.MINUTES);
        return order;
    }
}
