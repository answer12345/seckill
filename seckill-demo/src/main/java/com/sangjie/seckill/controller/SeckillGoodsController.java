package com.sangjie.seckill.controller;

import com.sangjie.seckill.Exception.LoginException;
import com.sangjie.seckill.pojo.GoodsSeckillVo;
import com.sangjie.seckill.pojo.Order;
import com.sangjie.seckill.pojo.SeckillMsg;
import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.service.GoodsService;
import com.sangjie.seckill.service.OrderService;
import com.sangjie.seckill.service.SeckillOrderService;
import com.sangjie.seckill.utils.JsonUtil;
import com.sangjie.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sangjie.seckill.rabbitmq.MQSender;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seckill")
public class SeckillGoodsController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    //判断redis中库存数量是否为0
    private Map<Long, Boolean> map = new HashMap<>();

    @RequestMapping("/doSeckill")
    public String doSeckill(User user, Model model, Long goodsId) {

        if (user == null) {
            return "login";
        }

        ValueOperations valueOperations = redisTemplate.opsForValue();
        //接口限流
        Integer times = (Integer) valueOperations.get("limit" + user.getId() + goodsId);
        if (times == null) {
            valueOperations.set("limit" + user.getId() + goodsId, 1, 60, TimeUnit.SECONDS);
        } else {
            if (times > 2) {
                model.addAttribute("errorMSG", RespBeanEnum.REQUEST_LIMIT.getMessage());
                return "error";
            }
            valueOperations.increment("limit" + user.getId() + goodsId);
        }

        GoodsSeckillVo goodsSeckillVo = goodsService.getGoodsVoById(goodsId);
        /*
        int stockNum = goodsSeckillVo.getStockCount();
        if (stockNum < 1) {
            model.addAttribute("errorMsg", RespBeanEnum.STOCK_ERROR.getMessage());
            return "error";
        }*/
        //判断是否重复购买
        Order order = (Order) valueOperations.get("seckillOrder" + goodsId + user.getId());
        if (order != null) {
            model.addAttribute("errorMsg", RespBeanEnum.STOCK_ERROR.getMessage());
            return "error";
        }
        //redis预减库存
        if (map.get(goodsId)) {
            model.addAttribute("errorMsg", RespBeanEnum.STOCK_ERROR.getMessage());
            return "error";
        }
        long stockNum = valueOperations.decrement("seckillGoods: "+goodsSeckillVo.getId());
        if (stockNum < 0) {
            valueOperations.increment("seckillGoods: "+goodsSeckillVo.getId());
            model.addAttribute("errorMsg", RespBeanEnum.STOCK_ERROR.getMessage());
            return "error";
        }

        //rabbitMQ进行秒杀
        SeckillMsg seckillMsg = new SeckillMsg(user, goodsId);
        mqSender.sendSeckillMsg(JsonUtil.oject2JsonStr(seckillMsg));
        model.addAttribute("orderStatus", 0);
        model.addAttribute("goodsId", goodsId);


        /*
        Long userId = user.getId();
        List<Long> goodsIds = seckillOrderService.getIdByUser(userId);
        for (Long eachId : goodsIds) {
            if (eachId == goodsId) {
                model.addAttribute("errorMsg", RespBeanEnum.MUTTIBUY_ERROR.getMessage());
                return "error";
            }
        }*/


        //Order newOrder = orderService.updateStockAndGetOrder(goodsSeckillVo, user);
        //model.addAttribute("order", newOrder);
        return "orderDetail";
    }

    @RequestMapping("/getResult")
    @ResponseBody
    public int getResult(Long goodsId, Model model, User user) {

        if (user == null) {
            throw new LoginException();
        }
        int res = seckillOrderService.getResult(goodsId, user.getId());
        return res;
    }

    /**
     * 在项目启动时候将商品加载进redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsSeckillVo> l = goodsService.getGoodsList();
        if (l == null) {
            return;
        }
        for (GoodsSeckillVo goodsSeckillVo : l) {
            redisTemplate.opsForValue().set("seckillGoods: "+goodsSeckillVo.getId(), goodsSeckillVo.getStockCount());
            map.put(goodsSeckillVo.getId(), false);
        }
    }
}
