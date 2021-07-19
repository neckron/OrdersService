package com.demo.service.service;

import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import com.demo.service.exception.NotFoundException;

import java.util.List;

public interface OrdersService {

    OrderSummaryResponseDTO submitOrder(final OrderRequestDTO orderRequestDTO);
    OrderSummaryResponseDTO getOrderById(final String orderId) throws NotFoundException;
    List<OrderSummaryResponseDTO> getAllOrders();
}
