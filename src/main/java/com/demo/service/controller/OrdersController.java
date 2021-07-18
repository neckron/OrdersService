package com.demo.service.controller;

import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import com.demo.service.service.OrdersService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@Validated
public class OrdersController {


    private OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping(value = "/" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderSummaryResponseDTO> submitOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        return ResponseEntity.created(null)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ordersService.submitOrder(orderRequest));
    }
}
