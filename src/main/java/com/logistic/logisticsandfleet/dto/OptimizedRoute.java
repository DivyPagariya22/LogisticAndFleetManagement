package com.logistic.logisticsandfleet.dto;

import java.util.List;

import com.logistic.logisticsandfleet.entity.City;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimizedRoute {
    List<City> cities;
    double distance;
    double time;
}
