package com.davinchicoder.spring.kafka.stream.infrastructure.event.producer;

import com.davinchicoder.events.user.validations.UserVerificationRequested;
import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.domain.UserEventProducer;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.mapper.UserEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userVerificationRequestedProducer")
@RequiredArgsConstructor
public class UserEventV1Producer implements UserEventProducer {

    private static final String BINDING_NAME = "userVerificationRequestedProducer-out-0";

    private final StreamBridge streamBridge;
    private final UserEventMapper userEventMapper;

    @Override
    public void sendUserVerificationRequested(User user) {

        UserVerificationRequested event = userEventMapper.maptoUserVerificationRequested(user);

        log.info("User verification requested event sent: {}", event);

        boolean send = streamBridge.send(BINDING_NAME, MessageBuilder.withPayload(event).build());
        if (!send) {
            log.error("Error sending user verification requested event");
        }
    }
}
