package com.demo.service.service;

import com.demo.service.domain.OrderRequest;
import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import com.demo.service.exception.NotFoundException;
import com.demo.service.repository.OrderRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class OrdersServiceImplTest {

    OrderRequestRepository orderRequestRepository = mock(OrderRequestRepository.class);

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @BeforeEach
    private void setUp() {
        Map<String,Double> pricesMap = new HashMap<>();
        pricesMap.put("apple",60d);
        pricesMap.put("orange",25d);
        this.ordersService = new OrdersServiceImpl(pricesMap,this.orderRequestRepository);
    }

    @Test
    void test_submitOrder_happyPath(){

        OrderRequestDTO validOrderRequest = OrderRequestDTO.builder()
                .goods(Arrays.asList(
                        GoodsOrderRequestDTO.builder().goodId("apple").quantity(1).build(),
                        GoodsOrderRequestDTO.builder().goodId("orange").quantity(1).build()
                )).build();

        when(orderRequestRepository.save(any())).thenReturn(buildOrderRequest());

        OrderSummaryResponseDTO summaryResponseDTO = ordersService.submitOrder(validOrderRequest);
        assertEquals(170d,summaryResponseDTO.getCost());
        assertEquals(4,summaryResponseDTO.getGoodOrderResponse().get(0).getQuantity());
        assertEquals(3,summaryResponseDTO.getGoodOrderResponse().get(1).getQuantity());
        assertEquals("123456",summaryResponseDTO.getOrderId());
    }

    @Test
    void test_getOrderById_happyPath() throws NotFoundException {

        when(orderRequestRepository.findById(any())).thenReturn(Optional.of(buildOrderRequest()));
        OrderSummaryResponseDTO summaryResponseDTO = ordersService.getOrderById("123456");

        assertEquals(170d,summaryResponseDTO.getCost());
        assertEquals(4,summaryResponseDTO.getGoodOrderResponse().get(0).getQuantity());
        assertEquals(3,summaryResponseDTO.getGoodOrderResponse().get(1).getQuantity());
        assertEquals("123456",summaryResponseDTO.getOrderId());
    }

    @Test
    void test_getOrderById_exception_orderNotFound()  {

        when(orderRequestRepository.findById(any())).thenReturn(Optional.empty());
        try {
            ordersService.getOrderById("123456");
            fail();
        } catch (NotFoundException e) {
            assertEquals("::: Can't find Order with id 123456",e.getMessage());
        }
    }


    @Test
    void test_getAllOrders_happyPath() {
        when(orderRequestRepository.findAll()).thenReturn(buildOrderRequestList());
        List<OrderSummaryResponseDTO> summaryResponseDTOList = ordersService.getAllOrders();
        assertEquals(2,summaryResponseDTOList.size());
        assertEquals(170d,summaryResponseDTOList.get(0).getCost());
        assertEquals(120d,summaryResponseDTOList.get(0).getGoodOrderResponse().get(0).getCost());
        assertEquals(4,summaryResponseDTOList.get(0).getGoodOrderResponse().get(0).getQuantity());
        assertEquals("apple",summaryResponseDTOList.get(0).getGoodOrderResponse().get(0).getGoodId());
        assertEquals("123456",summaryResponseDTOList.get(0).getOrderId());

    }

    private OrderRequest buildOrderRequest() {
        return OrderRequest.builder()
                .cost(170d)
                .goods(Arrays.asList(
                        GoodsOrderResponseDTO.builder().goodId("apple").cost(120d).quantity(4).build(),
                        GoodsOrderResponseDTO.builder().goodId("orange").cost(25d).quantity(3).build()
                ))
                .id("123456")
                .build();
    }

    private List<OrderRequest> buildOrderRequestList() {
        return Arrays.asList(buildOrderRequest(), buildOrderRequest());
    }

}
