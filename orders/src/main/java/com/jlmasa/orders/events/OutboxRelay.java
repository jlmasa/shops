package com.jlmasa.orders.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;


public class OutboxRelay {
    private static final Logger log = LoggerFactory.getLogger(OutboxRelay.class);
    private final JdbcTemplate jdbc;
    private final KafkaTemplate<String, String> kafka;

    public OutboxRelay(JdbcTemplate jdbc, KafkaTemplate<String, String> kafka){
        this.jdbc = jdbc;
        this.kafka = kafka;
    }

    @Scheduled(fixedDelay = 500)
    public void publishPending(){
        var rows = jdbc.queryForList("SELECT id, topic,message_key,payload FROM outbox WHERE published = FALSE ORDER BY created_at LIMIT 100");

        for (var row : rows){
            try{
                kafka.send((String) row.get("topic"),(String) row.get("message_key"), (String) row.get("payload")).get(5, TimeUnit.SECONDS);
                jdbc.update("UPDATE outbox SET published = TRUE WHERE id = ?", row.get("id"));
            }catch (Exception e) {
                log.warn("Kafka unavailable, will retry:{}", e.getMessage());
                return;
            }
        }
    }

}
