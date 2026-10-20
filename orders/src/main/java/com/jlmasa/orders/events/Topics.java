package com.jlmasa.orders.events;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Topics {
    @Bean
    NewTopic orders() {
        return TopicBuilder.name("orders.events").partitions(3).replicas(1).build();
    }
    @Bean
    NewTopic inventory() {
        return TopicBuilder.name("inventory.events").partitions(3).replicas(1).build();
    }
    @Bean
    NewTopic payments() {
        return TopicBuilder.name("payments.events").partitions(3).replicas(1).build();
    }

}
