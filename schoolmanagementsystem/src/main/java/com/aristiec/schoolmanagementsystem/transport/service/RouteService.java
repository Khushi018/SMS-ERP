package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.RouteDTO;
import com.aristiec.schoolmanagementsystem.transport.dto.VehicleDto;

public interface RouteService {
    RouteDTO createRoute(RouteDTO dto);

    List<RouteDTO> getAllRoutes();

    RouteDTO getRouteById(Long id);

    void deleteRoute(Long id);

}
