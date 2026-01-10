package com.davinchicoder.spring.kafka.stream.infrastructure.event.producer;

import com.davinchicoder.events.user.validations.UserVerificationRequested;
import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.domain.UserEventProducer;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.config.StreamBridgeWrapper;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.mapper.UserEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

        Message<UserVerificationRequested> message = MessageBuilder.withPayload(event)
                .copyHeaders(getHeaders(event))
                .build();

        boolean send = userVerificationRequestedStreamBridgeWrapper.send(message);
        if (!send) {
            log.error("Error sending user verification requested event");
        }
    }

    private Map<String, Object> getHeaders(UserVerificationRequested event) {
        HashMap<String, Object> headers = new HashMap<>();
        //Key for partitioning
        headers.put(KafkaHeaders.KEY, event.getEmail());
        headers.put("kafka_id", event.getId());

        return headers;
    }
}
