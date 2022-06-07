package com.sangjie.seckill.service.impl;

import com.sangjie.seckill.mapper.GoodsMapper;
import com.sangjie.seckill.pojo.GoodsSeckillVo;
import com.sangjie.seckill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsSeckillVo> getGoodsList() {

        return goodsMapper.getGoodsList();
    }

    @Override
    public GoodsSeckillVo getGoodsVoById(Long goodsId) {
        return goodsMapper.getGoodsVoById(goodsId);
    }
}
