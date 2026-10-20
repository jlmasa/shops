package com.jlmasa.orders.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PlaceOrderRequest(@NotBlank String customerId, @NotBlank String sku, @Positive int quantity, @NotNull @Positive
                                BigDecimal amount) {
}
