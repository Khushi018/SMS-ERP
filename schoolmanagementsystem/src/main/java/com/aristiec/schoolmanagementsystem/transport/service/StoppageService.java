package com.aristiec.schoolmanagementsystem.transport.service;

import java.util.List;

import com.aristiec.schoolmanagementsystem.transport.dto.StoppageDto;

public interface StoppageService {
    StoppageDto create(StoppageDto dto);

    List<StoppageDto> getByRoute(Long routeId);

    void delete(Long id);

    List<StoppageDto> getAllStoppages();

    List<StoppageDto> createGroupedStoppages(String groupName, List<StoppageDto> dtos);
}