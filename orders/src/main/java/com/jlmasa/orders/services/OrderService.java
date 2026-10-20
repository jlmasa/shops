package com.jlmasa.orders.services;

import com.jlmasa.orders.dtos.PlaceOrderRequest;
import com.jlmasa.orders.entities.Order;
import com.jlmasa.orders.events.IdempotencyGuard;
import com.jlmasa.orders.events.OrderEvent;
import com.jlmasa.orders.events.Outbox;
import com.jlmasa.orders.repo.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final Outbox outbox;
    private final IdempotencyGuard guard;
    private final ObjectMapper json;

    public OrderService(OrderRepository orders, Outbox outbox, IdempotencyGuard guard, ObjectMapper json){
        this.orders = orders;
        this.outbox = outbox;
        this.guard = guard;
        this.json = json;
    }
    @Transactional
    public Order place(PlaceOrderRequest r){
        Order o = orders.save(new Order(UUID.randomUUID(),r.customerId(),r.sku(),r.quantity(),r.amount()));
        outbox.add("orders.events", new OrderEvent(UUID.randomUUID(), "OrderCreated", o.getId(), o.getSku(), o.getQuantity(), o.getAmount()));
        return o;
    }

    @KafkaListener(topics = {"inventory.events", "payment.events"})
    @Transactional
    public void onEvent(String message) throws Exception {
        OrderEvent e = json.readValue(message, OrderEvent.class);
        if(!guard.firstTime(e.eventId())) return;
        Order o = orders.findById(e.orderId()).orElseThrow();
        switch (e.type()){
            case "PaymentCompleted" -> o.setStatus("CONFIRMED");
            case "StockRejected", "PaymentFailed" -> o.setStatus("CANCELLED");
            default -> { }
        }
    }
}
