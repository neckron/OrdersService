package com.demo.service.controller;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import com.demo.service.service.OrdersServiceImpl;
import com.demo.service.util.TestsUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrdersController.class)
class OrdersControllerTest {

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
                        GoodsOrderRequestDTO.builder().goodId("apple").quantity(2).build() ,
                        GoodsOrderRequestDTO.builder().goodId("orange").quantity(2).build()
                )).build();
    }

    private List<GoodsOrderResponseDTO> buildOrderResponseDTOList() {
        return Arrays.asList(
                        GoodsOrderResponseDTO.builder().goodId("apple").quantity(2).cost(10d).build() ,
                        GoodsOrderResponseDTO.builder().goodId("orange").quantity(2).cost(50d).build()
                );
    }

    private OrderRequestDTO buildInvalidOrderRequestDTOList() {
        return OrderRequestDTO.builder()
                .goods(Arrays.asList(
                        GoodsOrderRequestDTO.builder().quantity(2).build() ,
                        GoodsOrderRequestDTO.builder().goodId("orange").build()
                )).build();
    }

    private OrderSummaryResponseDTO buildSummaryResponse() {
        return OrderSummaryResponseDTO.builder()
                .cost(60)
                .goodOrderResponse(buildOrderResponseDTOList())
                .build();
    }

}
