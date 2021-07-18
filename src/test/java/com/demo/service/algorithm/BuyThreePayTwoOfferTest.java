package com.demo.service.algorithm;

import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BuyThreePayTwoOfferTest {

    private BuyThreePayTwoOffer buyThreePayTwoOffer;
    private Map<String, GoodsOrderRequestDTO> orderMap = new HashMap<>();
    private Map<String, Double> goodPriceMap = new HashMap<>();

    @BeforeEach
    private void setUp(){
        buyThreePayTwoOffer = new BuyThreePayTwoOffer();
        this.goodPriceMap.put("apple",10d);
    }

    @Test
    void test_offer_happyPath(){
        GoodsOrderRequestDTO goodsOrderRequestDTO = GoodsOrderRequestDTO.builder()
                .goodId("apple")
                .quantity(4)
                .build();
        this.orderMap.put("apple",goodsOrderRequestDTO);
        GoodsOrderResponseDTO goodsOrderResponseDTO = this.buyThreePayTwoOffer.calculateOffer(this.orderMap,"apple",this.goodPriceMap);
        assertNotNull(goodsOrderResponseDTO);
        assertEquals("apple",goodsOrderResponseDTO.getGoodId());
        assertEquals(30d,goodsOrderResponseDTO.getCost());
        assertEquals(4,goodsOrderResponseDTO.getQuantity());
    }

    @Test
    void test_offer_below3goods(){
        GoodsOrderRequestDTO goodsOrderRequestDTO = GoodsOrderRequestDTO.builder()
                .goodId("apple")
                .quantity(2)
                .build();
        this.orderMap.put("apple",goodsOrderRequestDTO);
        GoodsOrderResponseDTO goodsOrderResponseDTO = this.buyThreePayTwoOffer.calculateOffer(this.orderMap,"apple",this.goodPriceMap);
        assertNotNull(goodsOrderResponseDTO);
        assertEquals("apple",goodsOrderResponseDTO.getGoodId());
        assertEquals(20d,goodsOrderResponseDTO.getCost());
        assertEquals(2,goodsOrderResponseDTO.getQuantity());
    }

}
