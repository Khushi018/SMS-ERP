package com.aristiec.schoolmanagementsystem.service.details;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;

import java.util.List;

public interface CourseService {

    void deleteCourseById(Long id);

    CourseDTO addCourse(CourseDTO courseDTO);

    List<CourseDTO> getAllCourses();

    CourseDTO getCourseById(Long id);

    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    void deleteCourse(Long id);

}
