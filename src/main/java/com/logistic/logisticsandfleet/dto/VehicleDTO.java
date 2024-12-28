package com.logistic.logisticsandfleet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VehicleDTO(Long id,

        @NotNull(message = "Registration number cannot be null") String registrationNumber,

        String type,

        Double capacity,

        @NotNull(message = "Status cannot be null") String status,

        String lastMaintenanceDate) {
}
