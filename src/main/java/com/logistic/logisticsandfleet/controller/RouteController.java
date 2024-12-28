package com.logistic.logisticsandfleet.controller;

import com.logistic.logisticsandfleet.dto.OptimizedRoute;
import com.logistic.logisticsandfleet.entity.Route;
import com.logistic.logisticsandfleet.service.RouteOptimizationService;
import com.logistic.logisticsandfleet.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    private final RouteOptimizationService routeOptimizationService;

    public RouteController(RouteService routeService, RouteOptimizationService routeOptimizationService) {
        this.routeService = routeService;
        this.routeOptimizationService = routeOptimizationService;
    }

    @PostMapping
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        Route createdRoute = routeService.addRoute(route);
        return ResponseEntity.ok(createdRoute);
    }

    @GetMapping
    public ResponseEntity<List<Route>> getAllRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/optimized")
    public ResponseEntity<OptimizedRoute> getOptimizedRoutes(@RequestParam Long cityId1, @RequestParam Long cityId2) {
        try {
            OptimizedRoute optimizedRoute = routeOptimizationService.findOptimizedRoute(cityId1, cityId2, true);
            return ResponseEntity.ok(optimizedRoute);
        } catch (Exception e) {
            System.out.println("Error while finding optimized route: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
