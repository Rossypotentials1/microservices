package com.rossypotentials.product.service.repository;

import com.rossypotentials.product.service.model.Prodcut;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Prodcut, String> {
}
