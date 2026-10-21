package com.jlmasa.orders.events;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class IdempotencyGuard {
    private final JdbcTemplate jdbc;
    public IdempotencyGuard(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    /** true the first time an event id is seen, false for duplicates. */
    public boolean firstTime(UUID eventId){
        return jdbc.update("INSERT INTO processed_events(event_id) VALUES(?) ON CONFLICT DO NOTHING", eventId) ==1;
    }
}
