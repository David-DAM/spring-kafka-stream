package com.davinchicoder.spring.kafka.stream.IT.helper;


import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.infrastructure.repository.UserMemoryRepository;
import org.awaitility.Durations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

import static org.awaitility.Awaitility.await;

@Service
public class DatabaseHelper {

    @Autowired
    private UserMemoryRepository userRepository;

    public User findByIdAndCondition(String id, Predicate<User> predicate) {

        await()
                .atLeast(Durations.ONE_HUNDRED_MILLISECONDS)
                .atMost(Durations.TEN_SECONDS)
                .with()
                .pollInterval(Durations.TWO_HUNDRED_MILLISECONDS)
                .ignoreException(Exception.class)
                .until(() -> userRepository.findById(id).isPresent() &&
                        predicate.test(userRepository.findById(id).get())
                );

        return userRepository.findById(id).orElseThrow();

    }

}
