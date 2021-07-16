package com.demo.service.controller;

import com.demo.service.dto.request.*;
import com.demo.service.dto.response.*;
import com.demo.service.service.*;
import com.demo.service.util.*;

import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrdersServiceImpl ordersService;

    public static final String SERVICE_PATH="/orders/";

    @Test
    void test_orders_post_happyPath() throws Exception {

        OrderRequestDTO validRequestBody = buildOrderRequestDTOList();
        when(ordersService.submitOrder(validRequestBody)).thenReturn(buildSummaryResponse());

        ResultActions result = this.mockMvc.perform(post(SERVICE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestsUtils.getJsonFromObject(validRequestBody)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TestsUtils.getJsonStringFromFile("test_orders_happyPath_resp.json"), true));

    }

    @Test
    void test_orders_post_badInput_no_request_body() throws Exception {
        ResultActions result = this.mockMvc.perform(post(SERVICE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));

        result.andExpect(status().isBadRequest());

    }

    @Test
    void test_orders_post_badInput_required_fields_not_set() throws Exception {
        String invalidRequestBody = TestsUtils.getJsonFromObject(buildInvalidOrderRequestDTOList());
        ResultActions result = this.mockMvc.perform(post(SERVICE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestBody));

        result.andExpect(status().isBadRequest());

    }

    @Test
    void test_orders_post_badInput_no_valid_quantity() throws Exception {


        ResultActions result = this.mockMvc.perform(post(SERVICE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestsUtils.getJsonStringFromFile("test_orders_post_badInput_required_no_valid_quantity.json")));

        result.andExpect(status().isBadRequest());

    }

    @Test
    void test_orders_post_unexpectedError() throws Exception {

        OrderRequestDTO validRequestBody = buildOrderRequestDTOList();
        when(ordersService.submitOrder(any())).thenThrow(new RuntimeException("Any unexpected error occurs!"));

        ResultActions result = this.mockMvc.perform(post(SERVICE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestsUtils.getJsonFromObject(validRequestBody)));

        result.andExpect(status().isInternalServerError());

    }

    private OrderRequestDTO buildOrderRequestDTOList() {
        return OrderRequestDTO.builder()
                .goods(Arrays.asList(
                        GoodsOrderDTO.builder().goodId("apple").quantity(2).build() ,
                        GoodsOrderDTO.builder().goodId("orange").quantity(2).build()
                )).build();
    }

    private OrderRequestDTO buildInvalidOrderRequestDTOList() {
        return OrderRequestDTO.builder()
                .goods(Arrays.asList(
                        GoodsOrderDTO.builder().quantity(2).build() ,
                        GoodsOrderDTO.builder().goodId("orange").build()
                )).build();
    }

    private OrderSummaryResponseDTO buildSummaryResponse() {
        return OrderSummaryResponseDTO.builder()
                .cost(170)
                .orderRequest(buildOrderRequestDTOList())
                .build();
    }

}
