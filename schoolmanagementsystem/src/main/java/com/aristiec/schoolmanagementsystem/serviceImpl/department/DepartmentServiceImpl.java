package com.aristiec.schoolmanagementsystem.serviceImpl.department;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.dto.department.DepartmentDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.aristiec.schoolmanagementsystem.repository.department.DepartmentRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.service.department.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
     
    @Autowired
     private DepartmentRepository departmentRepository;
      
     @Autowired
     private CourseRepository courseRepository;

    @Override
    public Department addDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
         return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    @Override
    public List<Department> getAllDepartments() {
                return departmentRepository.findAll();

    }

    @Override
    public Department updateDepartment(Long id, DepartmentDTO departmentDTO) {
         Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public String assignCourseToDepartment(Long departmentId, Long courseId) {
             
        Department department=departmentRepository.findById(departmentId)
            .orElseThrow(()->new RuntimeException("Department not found"));

        Course course=courseRepository.findById(courseId)
          .orElseThrow(()->new RuntimeException("Course not found"));

          course.setDepartment(department);
          courseRepository.save(course);

          return "course assigned to department successfully.";
    }

    @Override
    public List<CourseDTO> getCoursesByDepartment(Long departmentId) {
          Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    return department.getCourses()
            .stream()
            .map(course -> {
                CourseDTO dto = new CourseDTO();
                dto.setId(course.getId());
                dto.setCourseName(course.getCourseName());
                dto.setCourseCode(course.getCourseCode());
                dto.setDurationInYears(course.getDurationInYears());
                dto.setTotalFees(course.getTotalFees());
                dto.setDescription(course.getDescription());
                dto.setIsSemesterSystem(course.getIsSemesterSystem());
                dto.setActive(course.getActive());
                return dto;
            }).collect(Collectors.toList());

    }
    
}
