package com.sangjie.seckill.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsSeckillVo extends Goods{
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;

    public GoodsSeckillVo(Long id, String goodsName, String goodsTitle, String goodsImg,
                          String goodsDetail, BigDecimal goodsPrice, Integer goodsStock, BigDecimal seckillPrice,
                          Integer stockCount, Date startDate, Date endDate) {
        super(id, goodsName, goodsTitle, goodsImg, goodsDetail, goodsPrice, goodsStock);
        this.seckillPrice = seckillPrice;
        this.stockCount = stockCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GoodsSeckillVo() {
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "GoodsSeckillVo{" +
                "seckillPrice=" + seckillPrice +
                ", stockCount=" + stockCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
