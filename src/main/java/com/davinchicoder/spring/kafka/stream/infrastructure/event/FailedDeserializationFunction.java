package com.davinchicoder.spring.kafka.stream.infrastructure.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;

import java.util.function.Function;

@Slf4j
public class FailedDeserializationFunction<T> implements Function<FailedDeserializationInfo, T> {

    @Override
    public T apply(FailedDeserializationInfo failedDeserializationInfo) {
        log.error("Failed to deserialize message", failedDeserializationInfo.getException());
        return null;
    }
}