package com.aristiec.schoolmanagementsystem.controller.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.dto.department.AssignCourseToDepartmentDTO;
import com.aristiec.schoolmanagementsystem.dto.department.DepartmentDTO;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.aristiec.schoolmanagementsystem.service.department.DepartmentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;



/**
 * Controller for managing Department-related operations in the School Management System.
 * Provides endpoints for CRUD operations, course assignment, and department-course queries.
 *
 * <p>
 * Endpoints:
 * <ul>
 *   <li>Create a new department</li>
 *   <li>Retrieve department by ID</li>
 *   <li>Retrieve all departments</li>
 *   <li>Update department details</li>
 *   <li>Delete a department</li>
 *   <li>Assign a course to a department</li>
 *   <li>Get courses by department</li>
 * </ul>
 * </p>
 *
 * @Operation(summary = "Department Management Controller",
 *            description = "Handles CRUD operations, course assignment, and department-course queries.")
 */
@RestController
@RequestMapping("/api/v1/departments")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Department Management", description = "Manage departments and their related courses and staff")
public class DepartmentController {
      
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.addDepartment(departmentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted with id: " + id);
    }

     @PostMapping("/assign-course")
    public ResponseEntity<String> assignCourseToDepartment(@RequestBody AssignCourseToDepartmentDTO dto) {
        String result = departmentService.assignCourseToDepartment(dto.getDepartmentId(), dto.getCourseId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{departmentId}/courses")
public ResponseEntity<List<CourseDTO>> getCoursesByDepartment(@PathVariable Long departmentId) {
    List<CourseDTO> courses = departmentService.getCoursesByDepartment(departmentId);
    return ResponseEntity.ok(courses);
}
    
}
