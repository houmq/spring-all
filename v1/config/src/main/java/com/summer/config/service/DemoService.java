package com.summer.config.service;

import com.summer.config.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public String show(){
        String show = demoDao.show();
        System.out.println(show);
        return "SUCCESS";
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer add(){
        Integer add = demoDao.add("哈哈");
        int i = 1 / 0;
        return add;
    }

    public void createTableIfNotExists(){
        demoDao.createTableIfNotExists();
    }

    public String getUserName(Integer userId){
        return demoDao.getUserName(userId);
    }

    public Integer addOrder(String name){
        return demoDao.addOrder(name);
    }

    public List<Map<String, Object>> getOrder(){
        return demoDao.getOrder();
    }

    public List<Map<String, Object>> getOrderById(String id){
        return demoDao.getOrderById(Long.valueOf(id));
    }


}
