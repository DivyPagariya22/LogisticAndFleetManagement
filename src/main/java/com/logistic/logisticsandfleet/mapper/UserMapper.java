package com.logistic.logisticsandfleet.mapper;

import org.springframework.stereotype.Component;

import com.logistic.logisticsandfleet.dto.UserDTO;
import com.logistic.logisticsandfleet.entity.User;

@Component
public class UserMapper {

    public static User toEntity(UserDTO userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(userDto.password())
                .role(User.Role.valueOf(userDto.role().toUpperCase()))
                .build();
    }

    public static UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }

    public static UserDTO toDtoExcludingPassword(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build(); // Excluding password
    }
}
