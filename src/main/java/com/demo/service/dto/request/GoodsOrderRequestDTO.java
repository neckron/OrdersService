package com.demo.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsOrderRequestDTO {

    @Min(value = 1 , message = "You should buy 1 item at least")
    private int quantity;
    @NotEmpty(message = "You should specify a valid goodId")
    private String goodId;
}
