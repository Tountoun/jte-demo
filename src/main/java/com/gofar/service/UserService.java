package com.gofar.service;

import com.gofar.entity.User;
import com.gofar.exception.UserException;
import com.gofar.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Email already exists");
        }
        userRepository.save(user);
    }
}
