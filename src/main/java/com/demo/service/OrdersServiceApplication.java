package com.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class OrdersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    @Bean(name = "goodsPriceMap")
    public Map<String,Double> getGoodsPriceMap(){
        Map<String,Double> goodsPriceMap = new HashMap<>();
        goodsPriceMap.put("apple",60d);
        goodsPriceMap.put("orange", 25d);
        return goodsPriceMap;
    }

}
