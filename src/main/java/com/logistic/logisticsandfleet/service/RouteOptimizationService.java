package com.logistic.logisticsandfleet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistic.logisticsandfleet.dto.OptimizedRoute;
import com.logistic.logisticsandfleet.entity.City;
import com.logistic.logisticsandfleet.entity.Route;
import com.logistic.logisticsandfleet.repository.CityRepository;
import com.logistic.logisticsandfleet.repository.RouteRepository;

import java.util.*;

@Service
public class RouteOptimizationService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RouteRepository routeRepository;

    private Map<Long, Map<Long, RouteInfo>> graph;

    private static class RouteInfo {
        double distance;
        double time;
        Route route;

        RouteInfo(Route route) {
            this.distance = route.getDistance();
            this.time = route.getTravelTime();
            this.route = route;
        }
    }

    public RouteOptimizationService() {
        this.graph = new HashMap<>();
    }

    public void buildGraph() {
        List<City> cities = cityRepository.findAll();
        List<Route> routes = routeRepository.findAll();
        graph.clear();

        for (City city : cities) {
            graph.put(city.getId(), new HashMap<>());
        }

        for (Route route : routes) {
            Long sourceId = route.getSourceCity().getId();
            Long destId = route.getDestinationCity().getId();

            graph.get(sourceId).put(destId, new RouteInfo(route));

            Route reverseRoute = new Route();
            reverseRoute.setSourceCity(route.getDestinationCity());
            reverseRoute.setDestinationCity(route.getSourceCity());
            reverseRoute.setDistance(route.getDistance());
            reverseRoute.setTravelTime(route.getTravelTime());
            graph.get(destId).put(sourceId, new RouteInfo(reverseRoute));
        }
    }

    public OptimizedRoute findOptimizedRoute(Long startCityId, Long endCityId, boolean byDistance) {
        if (graph.isEmpty()) {
            buildGraph();
        }
        return dijkstra(startCityId, endCityId, byDistance);
    }

    private OptimizedRoute dijkstra(Long startCityId, Long endCityId, boolean byDistance) {
        Map<Long, Double> distances = new HashMap<>();
        Map<Long, Double> times = new HashMap<>();
        Map<Long, Long> previousCities = new HashMap<>();
        Set<Long> visited = new HashSet<>();

        for (Long cityId : graph.keySet()) {
            distances.put(cityId, Double.MAX_VALUE);
            times.put(cityId, Double.MAX_VALUE);
        }
        distances.put(startCityId, 0.0);
        times.put(startCityId, 0.0);

        PriorityQueue<Long> pq = new PriorityQueue<>((a, b) -> Double
                .compare(byDistance ? distances.get(a) : times.get(a), byDistance ? distances.get(b) : times.get(b)));
        pq.offer(startCityId);

        while (!pq.isEmpty()) {
            Long currentCityId = pq.poll();

            if (visited.contains(currentCityId)) {
                continue;
            }

            visited.add(currentCityId);

            if (currentCityId.equals(endCityId)) {
                break;
            }

            for (Map.Entry<Long, RouteInfo> neighbor : graph.get(currentCityId).entrySet()) {
                Long neighborId = neighbor.getKey();
                RouteInfo routeInfo = neighbor.getValue();

                if (visited.contains(neighborId)) {
                    continue;
                }

                double newDistance = distances.get(currentCityId) + routeInfo.distance;
                double newTime = times.get(currentCityId) + routeInfo.time;

                if (byDistance ? newDistance < distances.get(neighborId) : newTime < times.get(neighborId)) {
                    distances.put(neighborId, newDistance);
                    times.put(neighborId, newTime);
                    previousCities.put(neighborId, currentCityId);
                    pq.offer(neighborId);
                }
            }
        }

        List<City> path = new ArrayList<>();
        for (Long cityId = endCityId; cityId != null; cityId = previousCities.get(cityId)) {
            final Long finalCityId = cityId; // Create final variable for lambda
            City city = cityRepository.findById(finalCityId)
                    .orElseThrow(() -> new RuntimeException("City not found with id: " + finalCityId));
            path.add(0, city);
        }

        OptimizedRoute result = new OptimizedRoute();
        result.setCities(path);

        if (path.size() > 1) {
            double totalDistance = 0;
            double totalTime = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                Long cityId = path.get(i).getId();
                Long nextCityId = path.get(i + 1).getId();
                RouteInfo routeInfo = graph.get(cityId).get(nextCityId);
                totalDistance += routeInfo.distance;
                totalTime += routeInfo.time;
            }
            result.setDistance(totalDistance);
            result.setTime(totalTime);
        } else {
            result.setDistance(0.0);
            result.setTime(0.0);
        }

        return result;
    }
}