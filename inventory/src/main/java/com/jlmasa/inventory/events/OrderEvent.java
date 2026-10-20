package com.jlmasa.inventory.events;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEvent(
        UUID eventId, String type, UUID orderId, String sku, int quantity, BigDecimal amount
) {
    /** A follow-up event for the same order, with a fresh id. */
    public OrderEvent next(String newType){
        return new OrderEvent(UUID.randomUUID(), newType, orderId, sku, quantity, amount);
    }
}
