package com.demo.service.service;

import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;

public interface OrdersService {

    OrderSummaryResponseDTO submitOrder(final OrderRequestDTO orderRequestDTO);
}
