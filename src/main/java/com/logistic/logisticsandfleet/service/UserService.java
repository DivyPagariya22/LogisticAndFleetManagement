package com.logistic.logisticsandfleet.service;

import org.springframework.stereotype.Service;

import com.logistic.logisticsandfleet.entity.User;
import com.logistic.logisticsandfleet.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        System.out.println("User created: " + user.getName());
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
