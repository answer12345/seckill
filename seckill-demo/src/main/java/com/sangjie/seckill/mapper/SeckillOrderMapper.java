package com.sangjie.seckill.mapper;

import com.sangjie.seckill.pojo.SeckillOrder;

import java.util.List;

public interface SeckillOrderMapper {

    List<Long> getIdByUser(Long userId);

    int create(SeckillOrder seckillOrder);

    SeckillOrder getSeckillOrderByUserAndGoodsId(Long goodsId, Long userId);
}
