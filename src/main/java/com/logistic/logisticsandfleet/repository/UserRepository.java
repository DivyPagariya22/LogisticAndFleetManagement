package com.logistic.logisticsandfleet.repository;

import com.logistic.logisticsandfleet.entity.User;
import com.logistic.logisticsandfleet.entity.User.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(Role role);

}