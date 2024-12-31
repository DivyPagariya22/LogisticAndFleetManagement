package com.logistic.logisticsandfleet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistic.logisticsandfleet.dto.ShipmentDTO;
import com.logistic.logisticsandfleet.service.ShipmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShipment(@RequestBody ShipmentDTO shipmentDTO) {
        try {
            String trackingId = shipmentService.createShipment(shipmentDTO);
            return ResponseEntity.ok("Tracking ID: " + trackingId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<ShipmentDTO> getShipment(@RequestParam String shipment_id) {
        try {
            ShipmentDTO shipmentDTO = shipmentService.getShipment(shipment_id);
            return ResponseEntity.ok(shipmentDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
