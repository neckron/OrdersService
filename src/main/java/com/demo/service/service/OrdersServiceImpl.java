package com.demo.service.service;

import com.demo.service.algorithm.BuyOneGetOtherFreeOffer;
import com.demo.service.algorithm.BuyThreePayTwoOffer;
import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService{

    private Map<String,Double> goodsPriceMap;

    public OrdersServiceImpl(Map<String, Double> goodsPriceMap) {
        this.goodsPriceMap = goodsPriceMap;
    }

    @Override
    public OrderSummaryResponseDTO submitOrder(final OrderRequestDTO orderRequest) {

        Map<String, GoodsOrderRequestDTO> requestMap = convertToMap(orderRequest);
        List<GoodsOrderResponseDTO> calculatedOffers = calculateOffers(requestMap);

        return OrderSummaryResponseDTO.builder()
                .cost(calculateTotalOrderCost(calculatedOffers))
                .goodOrderResponse(calculatedOffers)
                .build();
    }

    private List<GoodsOrderResponseDTO> calculateOffers(Map<String, GoodsOrderRequestDTO> requestMap) {
        BuyOneGetOtherFreeOffer buyOneGetOtherFreeOffer = new BuyOneGetOtherFreeOffer(); // change to provider
        BuyThreePayTwoOffer buyThreePayTwoOffer = new BuyThreePayTwoOffer(); // change to provider
        GoodsOrderResponseDTO goodOfferApplied = buyOneGetOtherFreeOffer.calculateOffer(requestMap,"apple", this.goodsPriceMap);
        GoodsOrderResponseDTO good2OfferApplied = buyThreePayTwoOffer.calculateOffer(requestMap,"orange", this.goodsPriceMap);
        return Arrays.asList(goodOfferApplied,good2OfferApplied);
    }

    private double calculateTotalOrderCost(List<GoodsOrderResponseDTO> goods) {
        return goods.stream()
                .map(GoodsOrderResponseDTO::getCost)
                .reduce(0d,Double::sum);
    }

    private Map<String, GoodsOrderRequestDTO> convertToMap(OrderRequestDTO orderRequest){
        return orderRequest.getGoods().stream()
                .collect(Collectors.toMap(GoodsOrderRequestDTO::getGoodId, Function.identity()));
    }

}
