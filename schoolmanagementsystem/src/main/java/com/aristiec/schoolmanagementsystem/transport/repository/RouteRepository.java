package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByName(String name);
}
