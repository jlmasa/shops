package com.jlmasa.orders.services;

import com.jlmasa.orders.dtos.PlaceOrderRequest;
import com.jlmasa.orders.entities.Order;
import com.jlmasa.orders.repo.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;
    private final OrderRepository orders;
    public OrderController(OrderService service, OrderRepository orders){
        this.service = service;
        this.orders = orders;
    }

    @PostMapping @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Object> place(@Valid @RequestBody PlaceOrderRequest r){
        Order o = service.place(r);
        return Map.of("id", o.getId(), "status", o.getStatus());
    }

    @GetMapping("/{id}")
    public Map<String, Object> get (@PathVariable UUID id){
        Order o = orders.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Map.of("id", o.getId(), "status", o.getStatus());
    }
}
