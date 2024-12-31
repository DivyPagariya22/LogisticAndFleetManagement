package com.logistic.logisticsandfleet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistic.logisticsandfleet.entity.Tracking;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {

}
