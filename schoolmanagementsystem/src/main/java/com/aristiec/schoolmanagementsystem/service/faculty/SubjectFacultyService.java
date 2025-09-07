package com.aristiec.schoolmanagementsystem.service.faculty;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.faculty.SubjectFacultyDTO;

public interface SubjectFacultyService {
    SubjectFacultyDTO assignFacultyToSubject(SubjectFacultyDTO subjectFacultyDTO);

    List<SubjectFacultyDTO> getAllAssignedFaculties();  

    SubjectFacultyDTO getAssignedFacultyById(Long id);

    void removeAssignedFaculty(Long id);  

    SubjectFacultyDTO updateAssignedFaculty(Long id, SubjectFacultyDTO subjectFacultyDTO);

}
