package com.davinchicoder.spring.kafka.stream.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(String id);

    void delete(String id);

    List<User> findAll();

}
