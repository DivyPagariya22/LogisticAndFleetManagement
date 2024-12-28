package com.logistic.logisticsandfleet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistic.logisticsandfleet.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
