package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.AttendDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class ParentDashboardDTO {
    private StudentDTO student;
    private List<FeesDTO>fees;
 private TotalAttendanceDTO totalAttendance;
    

}
