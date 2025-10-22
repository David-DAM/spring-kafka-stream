package com.davinchicoder.spring.kafka.stream.IT.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class KafkaHelper {

    private final static String validationTopic = "com.davinchicoder.events.user.validations.v1.verification.requested";

    @Autowired
    private KafkaConsumer<String, SpecificRecord> userValidationEventConsumer;

    public void refreshTopics() {
        this.cleanAllRecords();
        this.suscribeToConsumerTopics();
    }

    private void suscribeToConsumerTopics() {
        userValidationEventConsumer.subscribe(List.of(validationTopic));
    }

    private void cleanAllRecords() {
        this.suscribeToConsumerTopics();
        this.cleanTopics(userValidationEventConsumer);

    }

    @SafeVarargs
    private void cleanTopics(KafkaConsumer<String, SpecificRecord>... consumer) {
        Arrays.stream(consumer).forEach(KafkaHelper::cleanConsumer);
    }

    private static void cleanConsumer(KafkaConsumer<String, SpecificRecord> consumer) {
        try {
            while (true) {

                ConsumerRecords<String, SpecificRecord> records = consumer.poll(Duration.ofSeconds(1L));
                if (records.count() == 0) {
                    break;
                }
                consumer.commitSync();
            }
        } catch (WakeupException e) {
            log.info("Cleaning consumer due to wakeup exception");
        } catch (Exception e) {
            log.error("Error while cleaning consumer: {}", e.getMessage());
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.unsubscribe();
            }
        }
    }

    public ConsumerRecords<String, SpecificRecord> getUserVerificationRequestedEvent() {
        return userValidationEventConsumer.poll(Duration.ofMillis(5000));
    }
}
