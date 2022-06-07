package com.sangjie.seckill.rabbitmq;

import com.sangjie.seckill.Exception.BuyException;
import com.sangjie.seckill.pojo.GoodsSeckillVo;
import com.sangjie.seckill.pojo.Order;
import com.sangjie.seckill.pojo.SeckillMsg;
import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.service.GoodsService;
import com.sangjie.seckill.service.OrderService;
import com.sangjie.seckill.utils.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    /**
     * 下单
     */
    @RabbitListener(queues = "seckillQueue")
    public void receieve(String message) {
        SeckillMsg seckillMsg = JsonUtil.josonStr2Object(message, SeckillMsg.class);
        Long goodsId = seckillMsg.getGoodsId();
        User user = seckillMsg.getUser();
        GoodsSeckillVo goodsSeckillVo = goodsService.getGoodsVoById(goodsId);

        int stockNum = goodsSeckillVo.getStockCount();
        if (stockNum < 1) {
           redisTemplate.opsForValue().set(goodsId+"IsEmpty", true);
           return;
        }

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Order order = (Order) valueOperations.get("seckillOrder" + goodsId + user.getId());
        if (order != null) {
            return;
        }
        orderService.updateStockAndGetOrder(goodsSeckillVo, user);
    }
}
