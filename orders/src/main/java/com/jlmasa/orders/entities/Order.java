package com.jlmasa.orders.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name = "orders")
public class Order {
    @Id private UUID id;
    private String customerId;
    private String sku;
    private int quantity;
    private BigDecimal amount;
    private String status;

    protected Order() {}
    public Order(UUID id, String customerId, String sku, int quantity,
                 BigDecimal amount) {
        this.id = id; this.customerId = customerId; this.sku = sku;
        this.quantity = quantity; this.amount = amount; this.status =
                "PENDING";
    }
    public UUID getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getSku() { return sku; }
    public int getQuantity() { return quantity; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
