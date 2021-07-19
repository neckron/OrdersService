package com.demo.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class OrderSummaryResponseDTO {

    private double cost;
    private List<GoodsOrderResponseDTO> goodOrderResponse;
    private String orderId;

}
