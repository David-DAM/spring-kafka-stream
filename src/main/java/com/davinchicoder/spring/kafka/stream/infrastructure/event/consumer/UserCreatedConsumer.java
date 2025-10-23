package com.davinchicoder.spring.kafka.stream.infrastructure.event.consumer;

import com.davinchicoder.events.user.UserCreated;
import com.davinchicoder.spring.kafka.stream.application.UserService;
import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.domain.UserEventProducer;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.mapper.UserEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component("userCreatedConsumer")
public class UserCreatedConsumer implements Consumer<Message<UserCreated>> {

    private final UserService userService;
    private final UserEventProducer userEventProducer;
    private final UserEventMapper userEventMapper;

    @Override
    public void accept(Message<UserCreated> message) {
        log.info("User created event received: {}", message.getPayload());

        UserCreated event = message.getPayload();

        User user = userEventMapper.mapToUser(event);

        userService.createUser(user);

        userEventProducer.sendUserVerificationRequested(user);
    }
}
