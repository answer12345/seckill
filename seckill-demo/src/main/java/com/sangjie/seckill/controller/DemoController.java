package com.sangjie.seckill.controller;

import com.sangjie.seckill.service.DemoService;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "lisi");
        return "login";
    }

    /*
    @RequestMapping(value = "/put")
    @ResponseBody
    public Object put(String key, String value) {
        demoService.put(key, value);
        return "成功放入redis";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get(String key) {
        String value = demoService.get(key);

        return "数据Count为" + value;
    }*/
}
