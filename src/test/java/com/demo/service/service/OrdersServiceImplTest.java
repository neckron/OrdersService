package com.demo.service.service;

import com.demo.service.dto.request.*;
import com.demo.service.dto.response.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class OrdersServiceImplTest {

    @InjectMocks
    OrdersServiceImpl ordersService = new OrdersServiceImpl();

    @Test
    void test_submitOrder_happyPath(){
        OrderRequestDTO validOrderRequest = OrderRequestDTO.builder()
                .goods(Arrays.asList(
                        GoodsOrderDTO.builder().goodId("apple").quantity(1).build(),
                        GoodsOrderDTO.builder().goodId("orange").quantity(1).build()
                )).build();
        OrderSummaryResponseDTO summaryResponseDTO = ordersService.submitOrder(validOrderRequest);
        assertEquals(85d,summaryResponseDTO.getCost());
        assertEquals(validOrderRequest,summaryResponseDTO.getOrderRequest());
    }

    @Test
    void test_submitOrder_nullInput(){
        OrderSummaryResponseDTO summaryResponseDTO = ordersService.submitOrder(null);
        assertEquals(85d,summaryResponseDTO.getCost());

    }

}
