package com.logistic.logisticsandfleet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logistic.logisticsandfleet.entity.MaintenanceLog;

public interface MaintenaceLogRepository extends JpaRepository<MaintenanceLog, Long> {

}
