package com.jlmasa.orders.repo;

import com.jlmasa.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order , UUID> {
}
