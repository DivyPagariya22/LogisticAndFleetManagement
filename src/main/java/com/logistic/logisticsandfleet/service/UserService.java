package com.logistic.logisticsandfleet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.logistic.logisticsandfleet.dto.UserDTO;
import com.logistic.logisticsandfleet.entity.User;
import com.logistic.logisticsandfleet.entity.User.Role;
import com.logistic.logisticsandfleet.mapper.UserMapper;
import com.logistic.logisticsandfleet.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public String createUser(UserDTO userDto) {
        User user = UserMapper.toEntity(userDto);
        System.out.println("User created: " + user.getName());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
        return "Successfully Created User " + user.getName();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDTO getUserById(Long id) {
        System.out.println("Fetching user by id: " + id);
        User user;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error fetching user by id: " + e.getMessage());
        }
        return UserMapper.toDtoExcludingPassword(user);
    }

    public List<UserDTO> getAllUsers(String role) {

        List<User> users;

        if (role != null) {
            users = userRepository.findByRole(Role.valueOf(role.toUpperCase()));
        } else {
            users = userRepository.findAll();
        }

        return users.stream().map(user -> UserMapper.toDtoExcludingPassword(user)).collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (userDTO.name() != null)
            existingUser.setName(userDTO.name());
        if (userDTO.email() != null)
            existingUser.setEmail(userDTO.email());
        if (userDTO.role() != null)
            existingUser.setRole(Role.valueOf(userDTO.role().toUpperCase()));

        userRepository.save(existingUser);

        return UserMapper.toDtoExcludingPassword(existingUser);
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }

}
