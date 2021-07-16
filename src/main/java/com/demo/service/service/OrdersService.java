package com.demo.service.service;

import com.demo.service.dto.request.*;
import com.demo.service.dto.response.*;

public interface OrdersService {

    OrderSummaryResponseDTO submitOrder(OrderRequestDTO orderRequestDTO);
}
