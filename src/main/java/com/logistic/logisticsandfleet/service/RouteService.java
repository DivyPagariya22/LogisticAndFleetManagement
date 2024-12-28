package com.logistic.logisticsandfleet.service;

import com.logistic.logisticsandfleet.entity.Route;
import com.logistic.logisticsandfleet.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route addRoute(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
}
