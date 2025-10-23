package com.davinchicoder.spring.kafka.stream.application;

import com.davinchicoder.spring.kafka.stream.domain.User;
import com.davinchicoder.spring.kafka.stream.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteUser(String id) {
        userRepository.delete(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
