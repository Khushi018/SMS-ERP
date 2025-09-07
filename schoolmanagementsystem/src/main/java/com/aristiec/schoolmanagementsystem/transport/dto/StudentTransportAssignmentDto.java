package com.aristiec.schoolmanagementsystem.transport.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class StudentTransportAssignmentDto {
    private Long id;
    private String studentCode;
    private RouteDTO route;
    private VehicleDto vehicle;
    private List<StoppageDto> allRouteStoppages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StoppageDto dropOffStoppage;

}