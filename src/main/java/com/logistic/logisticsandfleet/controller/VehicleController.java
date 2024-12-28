package com.logistic.logisticsandfleet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistic.logisticsandfleet.dto.MaintenanceLogDTO;
import com.logistic.logisticsandfleet.dto.VehicleDTO;
import com.logistic.logisticsandfleet.entity.MaintenanceLog;
import com.logistic.logisticsandfleet.service.VehicleService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public String createVehicle(@RequestBody @Valid VehicleDTO vehicle) {
        try {
            vehicleService.createVehicle(vehicle);
            return "Vehicle created";
        } catch (Exception e) {
            return "Error creating vehicle";
        }
    }

    @PutMapping
    public String updateVehicle(@RequestParam Long id, @RequestBody VehicleDTO vehicle) {
        try {
            vehicleService.updateVehicle(id, vehicle);
            return "Vehicle updated";
        } catch (Exception e) {

            return e.getMessage();
        }
    }

    @GetMapping
    public ResponseEntity<VehicleDTO> getVehicleById(@RequestParam Long id) {
        try {
            VehicleDTO vehicle = vehicleService.getVehicleById(id);
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public String deleteVehicle(@RequestParam Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return "Vehicle deleted";
        } catch (Exception e) {
            return "Error deleting vehicle";
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<VehicleDTO>> getAllVehicleString() {
        try {
            List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/maintenance")
    public String addMaintenaceLog(@RequestBody MaintenanceLogDTO maintenanceLog) {
        vehicleService.addMaintenanceLog(maintenanceLog);
        return "Maintenance log added";
    }

    @GetMapping("/maintenance")
    public ResponseEntity<MaintenanceLog> getMethodName() {
        MaintenanceLog maintenanceLog = vehicleService.getMaintenanceLog();
        return ResponseEntity.ok(maintenanceLog);
    }

}
