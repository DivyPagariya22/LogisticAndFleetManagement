package com.logistic.logisticsandfleet.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistic.logisticsandfleet.dto.OptimizedRoute;
import com.logistic.logisticsandfleet.dto.ShipmentDTO;
import com.logistic.logisticsandfleet.entity.City;
import com.logistic.logisticsandfleet.entity.Shipment;
import com.logistic.logisticsandfleet.entity.Shipment.ShipmentStatus;
import com.logistic.logisticsandfleet.entity.Tracking;
import com.logistic.logisticsandfleet.entity.User;
import com.logistic.logisticsandfleet.entity.Vehicle.Status;
import com.logistic.logisticsandfleet.entity.Vehicle;
import com.logistic.logisticsandfleet.repository.CityRepository;
import com.logistic.logisticsandfleet.repository.ShipmentRepository;
import com.logistic.logisticsandfleet.repository.TrackingRepository;
import com.logistic.logisticsandfleet.repository.UserRepository;
import com.logistic.logisticsandfleet.repository.VehicleRepository;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteOptimizationService routeOptimizationService;

    @Autowired
    private TrackingRepository trackingRepository;

    public String createShipment(ShipmentDTO shipmentDTO) {

        // Check if Shipment is already present
        if (shipmentDTO.shipmentId() != null) {
            ShipmentDTO shipmentExists = getShipment(shipmentDTO.shipmentId());

            if (shipmentExists != null) {
                return "Shipment already exists";
            }
        }

        // Validate source and destination cities
        City sourceCity = cityRepository.findById(shipmentDTO.sourceCityId())
                .orElseThrow(() -> new RuntimeException("Source city not found"));
        City destinationCity = cityRepository.findById(shipmentDTO.destinationCityId())
                .orElseThrow(() -> new RuntimeException("Destination city not found"));

        // Validate User
        User user = userRepository.findById(shipmentDTO.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch available vehicles
        List<Vehicle> availableVehicles = vehicleRepository.findByStatus(Status.AVAILABLE);

        // Filter vehicles by capacity
        List<Vehicle> suitableVehicles = availableVehicles.stream()
                .filter(vehicle -> vehicle.getCapacity() >= shipmentDTO.weight())
                .sorted(Comparator.comparing(Vehicle::getCapacity).thenComparing(Vehicle::getLastMaintenanceDate,
                        Comparator.reverseOrder()))
                .toList();

        if (suitableVehicles.isEmpty()) {
            setShipmentToPending(sourceCity, destinationCity, user, shipmentDTO);
            return "No suitable vehicle available Shipemnt Pending";
        }

        // Assign the first vehicle from the sorted list
        Vehicle assignedVehicle = suitableVehicles.get(0);

        // Generate the optimized route
        OptimizedRoute optimizedRoute = routeOptimizationService.findOptimizedRoute(shipmentDTO.sourceCityId(),
                shipmentDTO.destinationCityId(), true);

        String optimizedRouteString = optimizedRoute.getCities().stream().map(City::getName)
                .reduce((city1, city2) -> city1 + "-" + city2).orElse("No route available");

        // Create and save the shipment
        Shipment shipment = new Shipment();
        shipment.setSourceCity(sourceCity);
        shipment.setDestinationCity(destinationCity);
        shipment.setUser(user);
        shipment.setWeight(shipmentDTO.weight());
        shipment.setStatus(ShipmentStatus.IN_TRANSIT);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setVehicle(assignedVehicle);
        shipment.setOptimizedRoute(optimizedRouteString);

        shipmentRepository.save(shipment);

        // Mark the vehicle as unavailable
        assignedVehicle.setStatus(Status.UNAVAILABLE);
        vehicleRepository.save(assignedVehicle);

        // Create a tracking entry
        Tracking tracking = new Tracking();
        tracking.setShipment(shipment);
        tracking.setLocation(sourceCity.getName());
        tracking.setTimestamp(LocalDateTime.now());
        trackingRepository.save(tracking);

        // Generate and return a tracking ID
        return "TRK" + shipment.getId();
    }

    public void setShipmentToPending(City sourceCity, City destinationCity, User user, ShipmentDTO shipmentDTO) {
        Shipment shipment = new Shipment();

        shipment.setSourceCity(sourceCity);
        shipment.setDestinationCity(destinationCity);
        shipment.setUser(user);
        shipment.setWeight(shipmentDTO.weight());
        shipment.setStatus(ShipmentStatus.PENDING);
        shipment.setCreatedAt(LocalDateTime.now());

        shipmentRepository.save(shipment);
    }

    public ShipmentDTO getShipment(String tracking_id) {

        if (!tracking_id.startsWith("TRK")) {
            throw new RuntimeException("Invalid tracking ID format");
        }

        Long shipmentId;
        try {
            shipmentId = Long.parseLong(tracking_id.substring(3));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid tracking ID format");
        }

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        ShipmentDTO shipmentDTO = new ShipmentDTO(shipment.getSourceCity().getId(),
                shipment.getDestinationCity().getId(), shipment.getUser().getId(), shipment.getWeight(),
                shipment.getStatus().name(), "TRK" + shipment.getId(), shipment.getOptimizedRoute());

        return shipmentDTO;
    }
}
