package com.aristiec.schoolmanagementsystem.transport.modal;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "route_timetables")
@Data
public class RouteTimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Route route;
    @ManyToOne
    private Stoppage stoppage;

    private LocalTime arrivalTime;
    private LocalTime departureTime;
}