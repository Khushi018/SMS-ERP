package com.aristiec.schoolmanagementsystem.dto.adminReport;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignationCountDTO {
     private String designation;
    private long count;
}
