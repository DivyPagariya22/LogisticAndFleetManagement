package com.logistic.logisticsandfleet.service;

import com.logistic.logisticsandfleet.entity.City;
import com.logistic.logisticsandfleet.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(City city) {
        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
