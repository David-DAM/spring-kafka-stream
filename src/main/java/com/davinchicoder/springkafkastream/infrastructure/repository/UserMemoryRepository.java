package com.davinchicoder.springkafkastream.infrastructure.repository;

import com.davinchicoder.springkafkastream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserMemoryRepository {

    private final List<User> users = new ArrayList<>();

    public void save(User user) {
        log.info("Saving user: {}", user);
        users.add(user);
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void delete(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }


}
