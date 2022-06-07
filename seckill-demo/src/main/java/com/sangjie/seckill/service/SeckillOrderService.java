package com.sangjie.seckill.service;

import java.util.List;

public interface SeckillOrderService {
    List<Long> getIdByUser(Long goodsId);


    int getResult(Long goodsId, Long id);
}
