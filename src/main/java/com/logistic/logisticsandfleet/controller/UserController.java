package com.logistic.logisticsandfleet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistic.logisticsandfleet.dto.UserDTO;
import com.logistic.logisticsandfleet.mapper.UserMapper;
import com.logistic.logisticsandfleet.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO user) throws IOException {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserByIdentifier(@RequestParam String identifier) {
        UserDTO userDTO;
        // Check if the identifier is an email (contains "@")
        if (identifier.contains("@")) {
            userDTO = UserMapper.toDtoExcludingPassword(userService.getUserByEmail(identifier));
        } else {
            try {
                // Try parsing the identifier as an ID (if it's a valid number)
                Long userId = Long.parseLong(identifier);
                userDTO = userService.getUserById(userId);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("/role")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false) String role) {
        List<UserDTO> users = userService.getAllUsers(role);
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestParam Long id,@RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
