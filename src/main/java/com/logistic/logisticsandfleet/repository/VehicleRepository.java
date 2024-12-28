package com.logistic.logisticsandfleet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistic.logisticsandfleet.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByType(String type);
}