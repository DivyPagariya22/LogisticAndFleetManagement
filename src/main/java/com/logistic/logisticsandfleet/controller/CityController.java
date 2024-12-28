package com.logistic.logisticsandfleet.controller;

import com.logistic.logisticsandfleet.entity.City;
import com.logistic.logisticsandfleet.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {
        City createdCity = cityService.addCity(city);
        return ResponseEntity.ok(createdCity);
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }
}
