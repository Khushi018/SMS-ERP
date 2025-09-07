package com.aristiec.schoolmanagementsystem.dto.schedule;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.OnlineExaminationDto;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.AssiDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingScheduleDTO {
    private List<AssiDTO> assignments;
    private List<ExamDTO> offlineExams;
    private List<OnlineExaminationDto> onlineExams;
}
