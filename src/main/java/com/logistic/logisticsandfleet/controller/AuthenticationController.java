package com.logistic.logisticsandfleet.controller;

import com.logistic.logisticsandfleet.config.JWTHelper;
import com.logistic.logisticsandfleet.dto.AuthenticationRequest;
import com.logistic.logisticsandfleet.dto.AuthenticationResponse;
import com.logistic.logisticsandfleet.entity.User;
import com.logistic.logisticsandfleet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTHelper jwtHelper;

    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthenticationRequest user) {

        if (user.getRole() == "ADMIN") {
            return ResponseEntity.badRequest().body("Cant Create Admin User");
        }

        // Check if the user already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setRole(User.Role.valueOf(user.getRole().toUpperCase()));

        // Save the new user
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        // Find the user by username
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Invalid username or password."));
        }

        // Validate the password
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("Invalid username or password."));
        }

        // Generate a JWT token
        String token = jwtHelper.generateToken(user.getId(), user.getRole().toString());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
