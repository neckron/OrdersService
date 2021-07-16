package com.demo.service.controller;

import com.demo.service.dto.request.*;
import com.demo.service.dto.response.*;
import com.demo.service.service.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.*;

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
