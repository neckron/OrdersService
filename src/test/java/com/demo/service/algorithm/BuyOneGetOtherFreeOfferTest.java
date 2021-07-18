package com.demo.service.algorithm;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BuyOneGetOtherFreeOfferTest {

    private BuyOneGetOtherFreeOffer buyOneGetOtherFreeOffer;
    private Map<String, GoodsOrderRequestDTO> orderMap = new HashMap<>();
    private Map<String, Double> goodPriceMap = new HashMap<>();

    @BeforeEach
    private void setUp(){
        buyOneGetOtherFreeOffer = new BuyOneGetOtherFreeOffer();
        GoodsOrderRequestDTO goodsOrderRequestDTO = GoodsOrderRequestDTO.builder()
                .goodId("apple")
                .quantity(2)
                .build();
        this.goodPriceMap.put("apple",10d);
        this.orderMap.put("apple",goodsOrderRequestDTO);
    }

    @Test
    void test_offer_happyPath(){
        GoodsOrderResponseDTO goodsOrderResponseDTO = this.buyOneGetOtherFreeOffer.calculateOffer(this.orderMap,"apple",this.goodPriceMap);
        assertNotNull(goodsOrderResponseDTO);
        assertEquals("apple",goodsOrderResponseDTO.getGoodId());
        assertEquals(20d,goodsOrderResponseDTO.getCost());
        assertEquals(4,goodsOrderResponseDTO.getQuantity());
    }


}
