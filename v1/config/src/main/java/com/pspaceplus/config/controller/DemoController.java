package com.pspaceplus.config.controller;

import com.pspaceplus.config.entity.DemoEntity;
import com.pspaceplus.config.service.DemoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class DemoController {

    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/demo")
    public DemoEntity hello(){
        logger.info("demo ----------------------------");
        String show = demoService.show();
        return new DemoEntity();
    }

    @GetMapping(value = "/add")
    public void add(){
        Integer add = demoService.add();
        System.out.println(add);
    }



    @GetMapping(value = "/create")
    public String dataTest(){
        demoService.createTableIfNotExists();
        return "SUCCESS";
    }

    @GetMapping(value = "/select")
    public String getUserName(@RequestParam(value = "user_id")Integer user_id){
        return demoService.getUserName(user_id);
    }

    @GetMapping(value = "/addorder")
    public Integer addOrder(@RequestParam(value = "name") String name){
        return demoService.addOrder(name);
    }

    @GetMapping(value = "/order")
    public List<Map<String, Object>> getOrder(@RequestParam(value = "id") String id){
        if (StringUtils.isBlank(id)){
            return demoService.getOrder();
        }
        return demoService.getOrderById(id);
    }



    @GetMapping(value = "/dtest")
    public String rtest(){
        logger.info("gateway request ================:{}", System.currentTimeMillis());
        return "is ok";
    }





}
