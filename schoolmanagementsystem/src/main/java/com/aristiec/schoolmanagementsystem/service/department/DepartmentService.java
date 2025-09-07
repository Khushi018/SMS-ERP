package com.aristiec.schoolmanagementsystem.service.department;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.dto.department.DepartmentDTO;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;

public interface DepartmentService {

    Department addDepartment(DepartmentDTO departmentDTO);
    Department getDepartmentById(Long id);
    List<Department> getAllDepartments();
    Department updateDepartment(Long id, DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
  String assignCourseToDepartment(Long departmentId, Long courseId);
  List<CourseDTO> getCoursesByDepartment(Long departmentId);


    
}
