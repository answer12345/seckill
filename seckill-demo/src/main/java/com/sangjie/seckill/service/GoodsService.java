package com.sangjie.seckill.service;

import com.sangjie.seckill.pojo.GoodsSeckillVo;

import java.util.List;

public interface GoodsService {
    List<GoodsSeckillVo> getGoodsList();

    GoodsSeckillVo getGoodsVoById(Long goodsId);
}
