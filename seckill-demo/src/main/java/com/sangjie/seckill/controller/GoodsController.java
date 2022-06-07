package com.sangjie.seckill.controller;

import com.sangjie.seckill.pojo.GoodsSeckillVo;
import com.sangjie.seckill.pojo.User;
import com.sangjie.seckill.service.GoodsService;
import com.sangjie.seckill.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserSerivce userSerivce;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        //User user = (User) request.getSession().getAttribute("user");

        //User user = userSerivce.getUser("user");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String loginHtml = (String) valueOperations.get("login");
        String goodsHtml = (String) valueOperations.get("goods");

        if (user == null) {
            if (loginHtml != null) {
                return loginHtml;
            }

            WebContext context = new WebContext(request, response, request.getServletContext(),
                    request.getLocale(), model.asMap());
            loginHtml = thymeleafViewResolver.getTemplateEngine().process("login", context);
            if (loginHtml != null) {
                valueOperations.set("login", loginHtml, 60, TimeUnit.SECONDS);

            }
            return loginHtml;
            //return "login";
        }

        if (goodsHtml != null) {
            return goodsHtml;
        }

        List<GoodsSeckillVo> goodsList = goodsService.getGoodsList();
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("user", user);

        WebContext goodsContext = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());

        goodsHtml = thymeleafViewResolver.getTemplateEngine().process("goods", goodsContext);
        if (goodsHtml != null) {
            valueOperations.set("goods", goodsHtml, 60, TimeUnit.SECONDS);
        }
        return goodsHtml;
        //return "goods";
    }

    @RequestMapping(value = "toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(@PathVariable Long goodsId, Model model, User user,
                           HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String loginHtml = (String) valueOperations.get("login");
        String goodsDetailHtml = (String) valueOperations.get("goodsDetail" + goodsId);
        if (user == null) {
            if (loginHtml != null) {
                return loginHtml;
            }
            WebContext context1 = new WebContext(request, response, request.getServletContext(),
                    request.getLocale(), model.asMap());
            loginHtml = thymeleafViewResolver.getTemplateEngine().process("login", context1);
            if (loginHtml != null) {
                valueOperations.set("login", loginHtml, 60, TimeUnit.SECONDS);
            }
            return loginHtml;
        }

        if (goodsDetailHtml != null) {
            return goodsDetailHtml;
        }

        GoodsSeckillVo goodsSeckillVo = goodsService.getGoodsVoById(goodsId);
        Date startDate = goodsSeckillVo.getStartDate();
        Date endDate = goodsSeckillVo.getEndDate();
        Date nowDate = new Date();
        int secKillStatus = 0;
        int remainTime = 0;
        if (nowDate.before(startDate)) {
            remainTime = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainTime = -1;
        } else {
            secKillStatus = 1;
            remainTime = 0;
        }
        model.addAttribute("remainTime", remainTime);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsSeckillVo);

        WebContext context2 = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        goodsDetailHtml = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context2);
        if (goodsDetailHtml != null) {
            valueOperations.set("goodsDetail" + goodsId, goodsDetailHtml, 60, TimeUnit.SECONDS);
        }
        return goodsDetailHtml;
        //return "goodsDetail";
    }

}
