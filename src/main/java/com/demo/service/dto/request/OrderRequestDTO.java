package com.demo.service.dto.request;

import lombok.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    @NotEmpty(message = "you should specified a list of goods")
    @Valid
    private List<GoodsOrderDTO> goods;

}
