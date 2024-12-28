package com.logistic.logisticsandfleet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.logistic.logisticsandfleet.dto.MaintenanceLogDTO;
import com.logistic.logisticsandfleet.dto.VehicleDTO;
import com.logistic.logisticsandfleet.entity.MaintenanceLog;
import com.logistic.logisticsandfleet.entity.Vehicle;
import com.logistic.logisticsandfleet.mapper.VehicleMapper;
import com.logistic.logisticsandfleet.repository.MaintenaceLogRepository;
import com.logistic.logisticsandfleet.repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final MaintenaceLogRepository maintenaceLogRepository;

    public VehicleService(VehicleRepository vehicleRepository, MaintenaceLogRepository maintenaceLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.maintenaceLogRepository = maintenaceLogRepository;
    }

    public void createVehicle(VehicleDTO vehicleDto) {
        Vehicle vehicle = VehicleMapper.toEntity(vehicleDto);
        try {
            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            throw new RuntimeException("Error creating vehicle: " + e.getMessage());
        }
    }

    public void updateVehicle(Long id, VehicleDTO vehicleDto) {
        try {
            Vehicle vehicle = VehicleMapper.toEntity(vehicleDto);
            System.out.println("-------------------------" + vehicle.getRegistrationNumber());
            Vehicle vehicleToUpdate = vehicleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

            if (vehicle.getRegistrationNumber() != null)
                vehicleToUpdate.setRegistrationNumber(vehicle.getRegistrationNumber());
            if (vehicle.getType() != null)
                vehicleToUpdate.setType(vehicle.getType());
            if (vehicle.getCapacity() != null)
                vehicleToUpdate.setCapacity(vehicle.getCapacity());
            if (vehicle.getStatus() != null)
                vehicleToUpdate.setStatus(vehicle.getStatus());
            if (vehicle.getLastMaintenanceDate() != null)
                vehicleToUpdate.setLastMaintenanceDate(
                        vehicle.getLastMaintenanceDate() != null ? vehicle.getLastMaintenanceDate() : null);

            vehicleRepository.save(vehicleToUpdate);

        } catch (RuntimeException e) {
            throw new RuntimeException("Error fetching vehicle by id: " + e.getMessage());
        }
    }

    public VehicleDTO getVehicleById(Long id) {
        try {
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
            return VehicleMapper.toDTO(vehicle);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error fetching vehicle by id: " + e.getMessage());
        }
    }

    public void deleteVehicle(Long id) {
        try {
            vehicleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting vehicle: " + e.getMessage());
        }
    }

    public List<VehicleDTO> getAllVehicles() {
        try {
            List<Vehicle> vehicles = vehicleRepository.findAll();
            return vehicles.stream().map(vehicle -> VehicleMapper.toDTO(vehicle)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching vehicles: " + e.getMessage());
        }
    }

    public void addMaintenanceLog(MaintenanceLogDTO maintenanceLog) {
        try {
            Vehicle vehicle = vehicleRepository.findById(maintenanceLog.vehicleId()).orElseThrow(
                    () -> new RuntimeException("Vehicle not found with id: " + maintenanceLog.vehicleId()));
            MaintenanceLog log = MaintenanceLog.builder().vehicle(vehicle)
                    .maintenanceDate(maintenanceLog.maintenanceDate()).description(maintenanceLog.description())
                    .cost(maintenanceLog.cost()).build();
            maintenaceLogRepository.save(log);
        } catch (Exception e) {
            throw new RuntimeException("Error adding maintenance log: " + e.getMessage());
        }
    }

    public MaintenanceLog getMaintenanceLog() {
        try {
            MaintenanceLog maintenanceLog = maintenaceLogRepository.findAll().get(0);
            return maintenanceLog;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching maintenance log: " + e.getMessage());
        }
    }
}
