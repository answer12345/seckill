package com.sangjie.seckill.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddId;
    private String goodsName;
    private Integer goodsCount;
    private BigDecimal goodsPrice;
    private Boolean orderChannel;
    private Boolean status;
    private Date createDate;
    private Date payDate;

    public Order() {}

    public Order(Long id, Long userId, Long goodsId, Long deliveryAddId, String goodsName,
                 Integer goodsCount, BigDecimal goodsPrice, Boolean orderChannel, Boolean status,
                 Date createDate, Date payDate) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.deliveryAddId = deliveryAddId;
        this.goodsName = goodsName;
        this.goodsCount = goodsCount;
        this.goodsPrice = goodsPrice;
        this.orderChannel = orderChannel;
        this.status = status;
        this.createDate = createDate;
        this.payDate = payDate;
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

    public Long getDeliveryAddId() {
        return deliveryAddId;
    }

    public void setDeliveryAddId(Long deliveryAddId) {
        this.deliveryAddId = deliveryAddId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Boolean getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Boolean orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", deliveryAddId=" + deliveryAddId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", goodsPrice=" + goodsPrice +
                ", orderChannel=" + orderChannel +
                ", status=" + status +
                ", createDate=" + createDate +
                ", payDate=" + payDate +
                '}';
    }
}
