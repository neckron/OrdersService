package com.demo.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsOrderResponseDTO {

    private int quantity;
    private String goodId;
    private Double cost;

}
