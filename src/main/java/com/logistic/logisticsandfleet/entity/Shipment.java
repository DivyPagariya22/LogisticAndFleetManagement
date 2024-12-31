package com.logistic.logisticsandfleet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_city_id", nullable = false)
    private City sourceCity;

    @ManyToOne
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = true)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Lob
    @Column(name = "optimized_route", columnDefinition = "TEXT")
    private String optimizedRoute;

    @Column(nullable = false)
    private Double weight;

    public enum ShipmentStatus {
        PENDING, IN_TRANSIT, DELIVERED
    }

}
