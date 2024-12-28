package com.logistic.logisticsandfleet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_city_id", nullable = false)
    private City sourceCity;

    @ManyToOne
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destinationCity;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Integer travelTime;
}
