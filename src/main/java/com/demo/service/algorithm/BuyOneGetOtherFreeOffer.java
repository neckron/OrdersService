package com.demo.service.algorithm;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;

import java.util.Map;

public class BuyOneGetOtherFreeOffer implements Offer{

    @Override
    public GoodsOrderResponseDTO calculateOffer(Map<String, GoodsOrderRequestDTO> orderMap, String goodToOffer, Map<String,Double> goodsPricesMap) {
        GoodsOrderRequestDTO goodsOrderRequestDTO = orderMap.get(goodToOffer);
        double cost = goodsOrderRequestDTO.getQuantity() * goodsPricesMap.get(goodToOffer);
        return GoodsOrderResponseDTO.builder()
                .quantity(goodsOrderRequestDTO.getQuantity()*2)
                .cost(cost)
                .goodId(goodToOffer)
                .build();
    }

}
