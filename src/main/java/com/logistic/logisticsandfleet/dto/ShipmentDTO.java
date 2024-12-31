package com.logistic.logisticsandfleet.dto;

import jakarta.validation.constraints.NotNull;

public record ShipmentDTO(

        @NotNull(message = "source city cannot be empty") Long sourceCityId,
        @NotNull(message = "destination city cannot be empty") Long destinationCityId,
        @NotNull(message = "user should be present") Long userId, Double weight, String Status, String shipmentId,
        String optimzedRoute) {

}
