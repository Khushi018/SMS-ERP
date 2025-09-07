package com.aristiec.schoolmanagementsystem.service.details;


import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.ParentDetailsDto;
import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.CourseExamMarksDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.ExamScheduleDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectMarksDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectWiseComparisonDTO;

public interface ParentService {
    ParentDetailsDto getParentDetailsByStudentId(Long studentId);
    ParentDetailsDto getParentDetailsByParentId(Long parentId);
    void deleteParentDetailsById(Long parentId);
    ParentDetailsDto getParentDetailsById(Long parentId);
    ParentDetailsDto getParentDetailsByEmail(String email);
    // CourseExamMarksDTO getMarksGroupedByCourseAndExamType(Long parentId);
 
  List<ExamScheduleDTO> getExamScheduleForParent(Long parentId);
  
}
