package com.logistic.logisticsandfleet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistic.logisticsandfleet.entity.Tracking;
import com.logistic.logisticsandfleet.service.ShipmentService;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<Tracking> getTracking(@RequestParam String shipment_id) {
        try {
            Tracking tracking = shipmentService.getTracking(shipment_id);
            return ResponseEntity.ok(tracking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
}
