package com.demo.service.service;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
class OrdersServiceImplTest {



    @InjectMocks
    private OrdersServiceImpl ordersService;

    @BeforeEach
    private void setUp() {
        Map<String,Double> pricesMap = new HashMap<>();
        pricesMap.put("apple",60d);
        pricesMap.put("orange",25d);
        this.ordersService = new OrdersServiceImpl(pricesMap);
    }

    @Test
    void test_submitOrder_happyPath(){
        OrderRequestDTO validOrderRequest = OrderRequestDTO.builder()
                .goods(Arrays.asList(
                        GoodsOrderRequestDTO.builder().goodId("apple").quantity(1).build(),
                        GoodsOrderRequestDTO.builder().goodId("orange").quantity(1).build()
                )).build();
        OrderSummaryResponseDTO summaryResponseDTO = ordersService.submitOrder(validOrderRequest);
        assertEquals(85d,summaryResponseDTO.getCost());
        assertEquals(2,summaryResponseDTO.getGoodOrderResponse().get(0).getQuantity());
        assertEquals(1,summaryResponseDTO.getGoodOrderResponse().get(1).getQuantity());
    }


}
