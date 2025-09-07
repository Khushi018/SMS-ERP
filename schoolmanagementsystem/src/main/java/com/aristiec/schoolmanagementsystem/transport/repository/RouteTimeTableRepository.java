package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.RouteTimeTable;

public interface RouteTimeTableRepository extends JpaRepository<RouteTimeTable, Long> {
    List<RouteTimeTable> findByRouteId(Long routeId);
}
