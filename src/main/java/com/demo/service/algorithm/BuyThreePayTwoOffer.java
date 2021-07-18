package com.demo.service.algorithm;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;

import java.util.Map;

public class BuyThreePayTwoOffer implements Offer{
    @Override
    public GoodsOrderResponseDTO calculateOffer(Map<String, GoodsOrderRequestDTO> orderMap, String goodOffer , Map<String,Double> goodsPricesMap) {
        GoodsOrderRequestDTO goodsOrderRequestDTO = orderMap.get(goodOffer);
        double goodOriginalCost = goodsPricesMap.get(goodOffer);
        int quantityToPay = 0;
        if(goodsOrderRequestDTO.getQuantity() % 3 == 0){
            quantityToPay = (goodsOrderRequestDTO.getQuantity() / 3)*2;
        }
        else {
            quantityToPay = (goodsOrderRequestDTO.getQuantity() / 3)*2 + goodsOrderRequestDTO.getQuantity() % 3;
        }
        return GoodsOrderResponseDTO.builder()
                .cost(quantityToPay*goodOriginalCost)
                .goodId(goodOffer)
                .quantity(goodsOrderRequestDTO.getQuantity())
                .build();

    }
}
