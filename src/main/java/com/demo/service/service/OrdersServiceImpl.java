package com.demo.service.service;

import com.demo.service.dto.request.*;
import com.demo.service.dto.response.*;
import org.springframework.stereotype.*;
import org.springframework.validation.annotation.*;

import javax.validation.*;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService{

    private static final double APPLE_VALUE = 60d;
    private static final double ORANGE_VALUE = 25d;

    @Override
    public OrderSummaryResponseDTO submitOrder(OrderRequestDTO orderRequest) {

        return OrderSummaryResponseDTO.builder()
                .cost(calculateOrderCost(orderRequest.getGoods()))
                .orderRequest(orderRequest)
                .build();

    }

    private double calculateOrderCost(List<GoodsOrderDTO> goods) {
        return goods.stream()
                .map(good -> {
                    if("apple".equals(good.getGoodId())) {
                        return good.getQuantity() * APPLE_VALUE;
                    }else {
                        return good.getQuantity() * ORANGE_VALUE;
                    }
                }).reduce(0d,Double::sum);
    }
}
