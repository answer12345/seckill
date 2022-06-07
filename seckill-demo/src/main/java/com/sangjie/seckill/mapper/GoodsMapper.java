package com.sangjie.seckill.mapper;

import com.sangjie.seckill.pojo.GoodsSeckillVo;

import java.util.List;

public interface GoodsMapper {
    List<GoodsSeckillVo> getGoodsList();

    GoodsSeckillVo getGoodsVoById(Long goodsId);
}
