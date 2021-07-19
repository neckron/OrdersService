package com.demo.service.repository;

import com.demo.service.domain.OrderRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRequestRepository  extends MongoRepository<OrderRequest, String> {

}
