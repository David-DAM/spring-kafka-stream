package com.davinchicoder.spring.kafka.stream.infrastructure.event.producer;

import com.davinchicoder.events.user.validations.UserVerificationRequested;
import com.davinchicoder.spring.kafka.stream.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Function;

@Slf4j
@Component("userVerificationRequestedProducer")
@RequiredArgsConstructor
public class UserVerificationRequestedProducer implements Function<User, Void> {

    private static final String BINDING_NAME = "userVerificationRequestedProducer-out-0";

    private final StreamBridge streamBridge;

    @Override
    public Void apply(User user) {

        UserVerificationRequested event = UserVerificationRequested.newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setTimestamp(Instant.now().toEpochMilli())
                .build();

        log.info("User verification requested event sent: {}", event);

        boolean send = streamBridge.send(BINDING_NAME, MessageBuilder.withPayload(event).build());
        if (!send) {
            log.error("Error sending user verification requested event");
        }
        return null;
    }
}
