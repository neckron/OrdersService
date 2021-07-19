package com.demo.service.service;

import com.demo.service.algorithm.BuyOneGetOtherFreeOffer;
import com.demo.service.algorithm.BuyThreePayTwoOffer;
import com.demo.service.domain.OrderRequest;
import com.demo.service.dto.request.GoodsOrderRequestDTO;
import com.demo.service.dto.request.OrderRequestDTO;
import com.demo.service.dto.response.GoodsOrderResponseDTO;
import com.demo.service.dto.response.OrderSummaryResponseDTO;
import com.demo.service.exception.NotFoundException;
import com.demo.service.repository.OrderRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService{

    private Map<String,Double> goodsPriceMap;
    private OrderRequestRepository orderRequestRepository;

    public OrdersServiceImpl(Map<String, Double> goodsPriceMap, OrderRequestRepository orderRequestRepository) {
        this.goodsPriceMap = goodsPriceMap;
        this.orderRequestRepository = orderRequestRepository;
    }

    @Override
    public OrderSummaryResponseDTO submitOrder(final OrderRequestDTO orderRequest) {

        Map<String, GoodsOrderRequestDTO> requestMap = convertToMap(orderRequest);
        List<GoodsOrderResponseDTO> calculatedOffers = calculateOffers(requestMap);
        Double cost = calculateTotalOrderCost(calculatedOffers);

        OrderRequest savedOrderRequest = orderRequestRepository.save(OrderRequest.builder()
                .goods(calculatedOffers)
                .cost(cost)
                .build());

        return OrderSummaryResponseDTO.builder()
                .cost(savedOrderRequest.getCost())
                .orderId(savedOrderRequest.getId())
                .goodOrderResponse(savedOrderRequest.getGoods())
                .build();
    }

    @Override
    public OrderSummaryResponseDTO getOrderById(String orderId) throws NotFoundException {
        OrderRequest foundOrderRequest =  this.orderRequestRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("::: Can't find Order with id "+orderId));
        return OrderSummaryResponseDTO.builder()
                .cost(foundOrderRequest.getCost())
                .orderId(foundOrderRequest.getId())
                .goodOrderResponse(foundOrderRequest.getGoods())
                .build();

    }

    @Override
    public List<OrderSummaryResponseDTO> getAllOrders() {
        List<OrderSummaryResponseDTO> allOrders = orderRequestRepository.findAll()
                .stream()
                .map(order -> OrderSummaryResponseDTO.builder()
                    .orderId(order.getId())
                    .cost(order.getCost())
                    .goodOrderResponse(order.getGoods())
                    .build()
                ).collect(Collectors.toList());
        return allOrders;
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
