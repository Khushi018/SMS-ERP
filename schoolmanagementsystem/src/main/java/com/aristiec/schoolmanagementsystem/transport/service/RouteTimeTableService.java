package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.RouteTimeTableDto;

public interface RouteTimeTableService {
    RouteTimeTableDto create(RouteTimeTableDto dto);

    List<RouteTimeTableDto> getByRoute(Long routeId);

    void delete(Long id);
}
