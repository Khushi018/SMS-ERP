package com.aristiec.schoolmanagementsystem.service.details;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.dto.ParentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.SemesterPerformanceDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentCourseSubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.StudentDetailsResponseDto;
import com.aristiec.schoolmanagementsystem.dto.StudentListResponseDto;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimeTableDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentDetailsService {
    StudentDetailsResponseDto admitStudent(StudentDetailsDto studentDetails);
    StudentDetailsResponseDto updateAdmittedStudent(Long studentId, StudentDetailsDto studentDetails);

    ParentDetailsDto getParentDetailsByStudentId(Long studentId);

    CourseDTO getCourseByStudentId(Long studentId);

    StudentDetailsResponseDto getStudentByCode(String studentCode);

    StudentDetailsResponseDto getStudentByEmail(String email);

    StudentDetailsResponseDto getStudentDetailsById(Long studentId);

    void deleteStudentById(Long studentId);

    List<StudentDetailsResponseDto> getAllStudents();

    Page<StudentDetailsResponseDto> getAllStudents(Pageable pageable);

    List<StudentDetailsResponseDto> getStudentsByCourseAndYear(String courseCode, String year);

    StudentListResponseDto getAllwithFilters(String courseCode, String year, String section, String level, String email,
            String mobileNumber, String aadharNumber, String gender, String firstName, String lastName);

    SectionEnum findAvailableSection(Course course, String year);

    StudentCourseSubjectMinimalDTO getStudentCourseAndSubjectNames(Long studentId);

   
}