package com.rossypotentials.order.service.repository;

import com.rossypotentials.order.service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}
