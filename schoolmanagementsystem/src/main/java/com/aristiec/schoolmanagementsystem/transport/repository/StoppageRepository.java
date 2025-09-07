package com.aristiec.schoolmanagementsystem.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.transport.modal.Stoppage;

public interface StoppageRepository extends JpaRepository<Stoppage, Long> {
    List<Stoppage> findByRouteId(Long routeId);
}
