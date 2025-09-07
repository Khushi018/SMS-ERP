package com.aristiec.schoolmanagementsystem.transport.dto;

import java.util.List;

import lombok.Data;

@Data
public class StoppageGroupResponseDto {
    private Long groupId;
    private List<StoppageDto> stoppages;
}