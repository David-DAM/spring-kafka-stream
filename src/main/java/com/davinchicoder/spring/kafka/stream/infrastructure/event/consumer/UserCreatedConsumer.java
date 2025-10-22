package com.davinchicoder.spring.kafka.stream.infrastructure.event.consumer;

import com.davinchicoder.events.user.UserCreated;
import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.infrastructure.event.producer.UserVerificationRequestedProducer;
import com.davinchicoder.spring.kafka.stream.infrastructure.repository.UserMemoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component("userCreatedConsumer")
public class UserCreatedConsumer implements Consumer<Message<UserCreated>> {

    private final UserMemoryRepository userMemoryRepository;
    private final UserVerificationRequestedProducer userVerificationRequestedProducer;

    @Override
    public void accept(Message<UserCreated> message) {
        log.info("User created event received: {}", message.getPayload());

        UserCreated event = message.getPayload();

        User user = User.builder()
                .id(event.getId())
                .name(event.getFirstname())
                .email(event.getEmail())
                .createdAt(LocalDateTime.ofEpochSecond(event.getTimestamp(), 0, ZoneOffset.UTC))
                .build();

        userMemoryRepository.save(user);

        userVerificationRequestedProducer.apply(user);
    }
}
