package com.aristiec.schoolmanagementsystem.transport.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stoppages")
@Data
public class Stoppage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Route route;

    @Column(nullable = false)
    private String name;

    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "stoppage_group_id")
    private StoppageGroup group;

    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "duration")
    private String duration;
}