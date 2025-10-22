package com.davinchicoder.spring.kafka.stream.IT;


import com.davinchicoder.events.user.UserCreated;
import com.davinchicoder.events.user.validations.UserVerificationRequested;
import com.davinchicoder.spring.kafka.stream.IT.helper.DatabaseHelper;
import com.davinchicoder.spring.kafka.stream.IT.helper.KafkaHelper;
import com.davinchicoder.spring.kafka.stream.domain.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIT {

    @Autowired
    private KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    private final static String USER_EVENT_TOPIC = "com.davinchicoder.events.user.v1.created";

    @Autowired
    private KafkaHelper kafkaHelper;

    @Autowired
    private DatabaseHelper databaseHelper;

    @BeforeEach
    void setUp() {
        kafkaHelper.refreshTopics();
    }

    @SneakyThrows
    @Test
    public void userCreatedEventShouldBeProcessed() {

        String userId = UUID.randomUUID().toString();

        UserCreated event = UserCreated.newBuilder()
                .setId(userId)
                .setFirstname("David")
                .setLastname("Jimenez")
                .setEmail("support@davinchicoder.dev")
                .setRole("USER")
                .setTimestamp(System.currentTimeMillis())
                .build();

        SendResult<String, SpecificRecord> result = kafkaTemplate.send(USER_EVENT_TOPIC, event).get();

        assertNotNull(result);

        log.info("User created event sent: {}", event);

        User user = databaseHelper.findByIdAndCondition(userId, Objects::nonNull);

        assertNotNull(user);
        assertEquals(event.getFirstname(), user.getName());

        ConsumerRecords<String, SpecificRecord> userValidationRecords = kafkaHelper.getUserVerificationRequestedEvent();

        assertNotNull(userValidationRecords);
        assertEquals(1, userValidationRecords.count());
        boolean isEventSent = StreamSupport.stream(userValidationRecords.spliterator(), false)
                .map(ConsumerRecord::value)
                .anyMatch(record -> UserVerificationRequested.getClassSchema().getFullName().equals(record.getSchema().getFullName()));

        assertTrue(isEventSent);
    }

}
