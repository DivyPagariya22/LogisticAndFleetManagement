package com.logistic.logisticsandfleet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistic.logisticsandfleet.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

}
