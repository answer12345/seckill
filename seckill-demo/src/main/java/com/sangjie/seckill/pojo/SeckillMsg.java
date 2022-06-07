package com.sangjie.seckill.pojo;

public class SeckillMsg {
    private User user;
    private Long goodsId;

    public SeckillMsg() {}

    public SeckillMsg(User user, Long goodsId) {
        this.user = user;
        this.goodsId = goodsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
