package com.aristiec.schoolmanagementsystem.service.subject;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.SubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.SubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultyDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultySubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.StudentSubjectsDTO;

public interface SubjectService {

    SubjectDTO createSubject(SubjectDTO dto);

    SubjectDTO getSubjectById(Long id);

    List<SubjectDTO> getAllSubjects();

    SubjectDTO updateSubject(Long id, SubjectDTO dto);

    void deleteSubject(Long id);

    List<SubjectMinimalDTO> getSubjectsByStudentCourseAndSemester(Long studentId, Long courseId, Integer semester);

    List<FacultySubjectDTO> getFacultiesBySubjectIdAndSection(Long subjectId, SectionEnum section);

    void uploadSubjectFile(Long subjectId, MultipartFile file);

    Resource downloadSubjectFile(Long subjectId, String filename);

}
