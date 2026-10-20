package com.jlmasa.orders.events;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class Outbox {
    private final JdbcTemplate jdbc;
    private final ObjectMapper json;
    public Outbox(JdbcTemplate jdbc, ObjectMapper json) { this.jdbc = jdbc;
        this.json = json; }
    /** Call inside the same @Transactional method as the business change. */
    public void add(String topic, OrderEvent event) {
        try {
            jdbc.update("INSERT INTO outbox(id, topic, message_key, payload) VALUES (?, ?, ?, ?)",
            UUID.randomUUID(), topic, event.orderId().toString(),
                    json.writeValueAsString(event));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

