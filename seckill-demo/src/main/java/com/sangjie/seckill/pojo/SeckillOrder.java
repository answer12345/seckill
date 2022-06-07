package com.sangjie.seckill.pojo;

public class SeckillOrder {

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long orderId;

    public SeckillOrder() {}

    public SeckillOrder(Long id, Long userId, Long goodsId, Long orderId) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "seckillOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", orderId=" + orderId +
                '}';
    }
}
