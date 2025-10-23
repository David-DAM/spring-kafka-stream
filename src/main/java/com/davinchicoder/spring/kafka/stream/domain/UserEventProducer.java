package com.davinchicoder.spring.kafka.stream.domain;

public interface UserEventProducer {

    void sendUserVerificationRequested(User user);

}
