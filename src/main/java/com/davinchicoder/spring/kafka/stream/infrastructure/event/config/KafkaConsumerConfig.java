package com.davinchicoder.spring.kafka.stream.infrastructure.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public RecordMessageConverter recordMessageConverter() {
        return new MessagingMessageConverter();
    }


}
