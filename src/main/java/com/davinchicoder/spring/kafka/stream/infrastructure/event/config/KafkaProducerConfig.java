package com.davinchicoder.spring.kafka.stream.infrastructure.event.config;

import com.davinchicoder.events.user.validations.UserVerificationRequested;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    private static final String BINDING_NAME = "userVerificationRequestedProducer-out-0";

    @Bean
    public StreamBridgeWrapper<UserVerificationRequested> userVerificationRequestedStreamBridgeWrapper(StreamBridge streamBridge) {
        return new StreamBridgeWrapper<>(BINDING_NAME, streamBridge);
    }


}
