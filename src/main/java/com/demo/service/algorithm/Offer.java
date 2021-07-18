package com.demo.service.algorithm;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;

import java.util.Map;

public interface Offer {

    GoodsOrderResponseDTO calculateOffer(final Map<String, GoodsOrderRequestDTO> orderMap, String goodToOffer, Map<String,Double> goodsPrices);

}
