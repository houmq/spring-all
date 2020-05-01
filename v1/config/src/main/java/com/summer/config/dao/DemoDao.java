package com.summer.config.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DemoDao {

    String show();

    Integer add(@Param("param") String param);


    void createTableIfNotExists();

    String getUserName(@Param("user_id")Integer userId);

    Integer addOrder(@Param("name") String name);

    List<Map<String, Object>> getOrder();

    List<Map<String, Object>> getOrderById(@Param("order_id") Long order_id);


}
