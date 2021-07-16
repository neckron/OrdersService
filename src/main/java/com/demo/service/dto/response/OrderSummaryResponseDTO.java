package com.demo.service.dto.response;

import com.demo.service.dto.request.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@Data
@Builder
public class OrderSummaryResponseDTO {

    private double cost;
    private OrderRequestDTO orderRequest;

}
