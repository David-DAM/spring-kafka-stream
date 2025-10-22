package com.davinchicoder.springkafkastream.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;

import java.util.function.Function;


public class FailedDeserializationFunction<T> implements Function<FailedDeserializationInfo, T> {

    private static final Logger log = LoggerFactory.getLogger(FailedDeserializationFunction.class);

    @Override
    public T apply(FailedDeserializationInfo failedDeserializationInfo) {
        log.error("Failed to deserialize message", failedDeserializationInfo.getException());
        return null;
    }
}