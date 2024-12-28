package com.logistic.logisticsandfleet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistic.logisticsandfleet.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByType(String type);

    List<Vehicle> findByLastMaintenanceDateBefore(LocalDate lastMaintenanceDate);

    Vehicle findByRegistrationNumberIgnoreCase(String registrationNumber);
}