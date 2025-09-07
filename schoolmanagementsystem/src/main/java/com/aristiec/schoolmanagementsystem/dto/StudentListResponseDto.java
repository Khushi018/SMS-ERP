package com.aristiec.schoolmanagementsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentListResponseDto {
    private long count;
    private List<StudentDetailsResponseDto> students;
}

