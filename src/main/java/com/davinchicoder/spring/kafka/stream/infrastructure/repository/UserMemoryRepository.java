package com.davinchicoder.spring.kafka.stream.infrastructure.repository;

import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserMemoryRepository implements UserRepository {

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
