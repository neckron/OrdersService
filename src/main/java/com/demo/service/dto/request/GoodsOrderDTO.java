package com.demo.service.dto.request;

import lombok.*;
import org.springframework.validation.annotation.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsOrderDTO {

    @Min(value = 1 , message = "You should buy 1 item at least")
    private int quantity;
    @NotEmpty(message = "You should specify a valid goodId")
    private String goodId;
}
