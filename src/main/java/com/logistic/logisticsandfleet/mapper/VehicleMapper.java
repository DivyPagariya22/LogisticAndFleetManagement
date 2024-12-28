package com.logistic.logisticsandfleet.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.logistic.logisticsandfleet.dto.VehicleDTO;
import com.logistic.logisticsandfleet.entity.Vehicle;

@Component
public class VehicleMapper {

    public static VehicleDTO toDTO(Vehicle vehicle) {
        return VehicleDTO.builder().id(vehicle.getId()).registrationNumber(vehicle.getRegistrationNumber())
                .type(vehicle.getType()).capacity(vehicle.getCapacity()).status(vehicle.getStatus().name())
                .lastMaintenanceDate(
                        vehicle.getLastMaintenanceDate() != null ? vehicle.getLastMaintenanceDate().toString() : null)
                .build();
    }

    public static Vehicle toEntity(VehicleDTO vehicleDTO) {
        return Vehicle.builder().id(vehicleDTO.id()).registrationNumber(vehicleDTO.registrationNumber())
                .type(vehicleDTO.type()).capacity(vehicleDTO.capacity())
                .status(Vehicle.Status.valueOf(vehicleDTO.status()))
                .lastMaintenanceDate(
                        vehicleDTO.lastMaintenanceDate() != null ? LocalDate.parse(vehicleDTO.lastMaintenanceDate())
                                : null)
                .build();
    }
}
