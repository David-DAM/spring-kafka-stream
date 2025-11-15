package com.davinchicoder.spring.kafka.stream.infrastructure.event.producer;

import com.davinchicoder.events.user.validations.UserVerificationRequested;
import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.domain.UserEventProducer;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.config.StreamBridgeWrapper;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.mapper.UserEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component("userVerificationRequestedProducer")
@RequiredArgsConstructor
public class UserEventV1Producer implements UserEventProducer {

    private final StreamBridgeWrapper<UserVerificationRequested> userVerificationRequestedStreamBridgeWrapper;
    private final UserEventMapper userEventMapper;

    @Override
    public void sendUserVerificationRequested(User user) {

        UserVerificationRequested event = userEventMapper.maptoUserVerificationRequested(user);

        log.info("User verification requested event sent: {}", event);

        boolean send = userVerificationRequestedStreamBridgeWrapper.send(MessageBuilder.withPayload(event).build());
        if (!send) {
            log.error("Error sending user verification requested event");
        }
    }
}
