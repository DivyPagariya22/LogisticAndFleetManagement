package com.logistic.logisticsandfleet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MaintenanceLogDTO(Long id,

        @NotNull(message = "Vehicle ID cannot be null") Long vehicleId,

        @NotNull(message = "Maintenance date cannot be null") LocalDate maintenanceDate,

        @NotNull(message = "Description cannot be null") String description,

        @NotNull(message = "Cost cannot be null") Double cost) {
}
