package com.sangjie.seckill.service;

import com.sangjie.seckill.Exception.BuyException;
import com.sangjie.seckill.pojo.GoodsSeckillVo;
import com.sangjie.seckill.pojo.Order;
import com.sangjie.seckill.pojo.User;

public interface OrderService {
    Order updateStockAndGetOrder(GoodsSeckillVo goodsSeckillVo, User user);
}
