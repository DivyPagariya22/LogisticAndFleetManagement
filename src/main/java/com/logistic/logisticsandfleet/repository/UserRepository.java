package com.logistic.logisticsandfleet.repository;

import com.logistic.logisticsandfleet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}