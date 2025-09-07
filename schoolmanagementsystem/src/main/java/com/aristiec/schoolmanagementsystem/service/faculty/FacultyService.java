package com.aristiec.schoolmanagementsystem.service.faculty;

import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.dto.faculty.AssignedSubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultyDTO;

public interface FacultyService {
     FacultyDTO addFaculty(FacultyDTO facultyDTO);
    FacultyDTO getFacultyById(Long id);
    FacultyDTO getFacultyByEmail(String email);
    List<FacultyDTO> getAllFaculties();
    FacultyDTO updateFaculty(Long id, FacultyDTO facultyDTO);
    void deleteFaculty(Long id);
    List<FacultyDTO> getFacultyByDepartment(Long departmentId);
   List<FacultyDTO> getAllHODs();
   List<FacultyDTO> getFacultyByPosition(FacultyDesignation facultyDesignation);
   List<AssignedSubjectDTO> getAssignedSubjectsWithSectionByFaculty(Long facultyId);

}
