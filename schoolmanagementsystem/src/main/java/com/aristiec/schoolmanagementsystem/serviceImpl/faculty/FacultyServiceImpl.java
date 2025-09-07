package com.aristiec.schoolmanagementsystem.serviceImpl.faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.faculty.AssignedSubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultyDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.aristiec.schoolmanagementsystem.repository.department.DepartmentRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.FacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.SubjectFacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.timetable.TimetableRepository;
import com.aristiec.schoolmanagementsystem.service.faculty.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService {
       @Autowired
    private FacultyRepository facultyRepository;
       @Autowired
    private DepartmentRepository departmentRepository;
     @Autowired
    private SubjectFacultyRepository subjectFacultyRepository;
     @Autowired
    private TimetableRepository timetableRepository;
     @Autowired
    private CourseRepository courseRepository;

      @Autowired
    private ModelMapper modelMapper;

    @Override
    public FacultyDTO addFaculty(FacultyDTO dto) {
       Faculty faculty = modelMapper.map(dto, Faculty.class);
        Department department = departmentRepository.findById(dto.getDepartmentId())
        .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
        faculty.setDepartment(department);

         List<Course> courses = new ArrayList<>();
    for (Long courseId : dto.getCourseIds()) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        courses.add(course);
    }
    faculty.setCourses(courses);

        return mapToDTO(facultyRepository.save(faculty));
    }

    @Override
    public FacultyDTO getFacultyById(Long id) {
       Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));
        return mapToDTO(faculty);

    }

    @Override
    public List<FacultyDTO> getAllFaculties() {
         return facultyRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
       }

    @Override
    public FacultyDTO updateFaculty(Long id, FacultyDTO dto) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        faculty.setFullName(dto.getFullName());
        faculty.setFacultyId(dto.getFacultyId());
        faculty.setEmail(dto.getEmail());
        faculty.setPhoneNumber(dto.getPhoneNumber());
        faculty.setPosition(dto.getPosition());
        faculty.setExperience(dto.getExperience());
        faculty.setQualification(dto.getQualification());
        faculty.setSalary(dto.getSalary());
        faculty.setJoiningDate(dto.getJoiningDate());

        Department department = departmentRepository.findById(dto.getDepartmentId())
        .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
     faculty.setDepartment(department);
       
      List<Course> courses = new ArrayList<>();
    for (Long courseId : dto.getCourseIds()) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        courses.add(course);
    }
      faculty.setCourses(courses);

        return mapToDTO(facultyRepository.save(faculty));

      }

    @Override
    public void deleteFaculty(Long id) {
                facultyRepository.deleteById(id);

     }

     private FacultyDTO mapToDTO(Faculty faculty) {
        FacultyDTO dto = modelMapper.map(faculty, FacultyDTO.class);
    dto.setDepartmentId(faculty.getDepartment().getId());
    dto.setCourseIds(faculty.getCourses().stream().map(Course::getId).collect(Collectors.toList()));
    return dto;
    }

     @Override
     public List<FacultyDTO> getFacultyByDepartment(Long departmentId) {
        return facultyRepository.findByDepartmentId(departmentId).stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
     }

     @Override
     public List<FacultyDTO> getAllHODs() {
       return facultyRepository.findByPosition(FacultyDesignation.HOD).stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
     }

     @Override
     public List<FacultyDTO> getFacultyByPosition(FacultyDesignation facultyDesignation) {
        return facultyRepository.findByPosition(facultyDesignation).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());

    }



     @Override
     public List<AssignedSubjectDTO> getAssignedSubjectsWithSectionByFaculty(Long facultyId) {
         List<Object[]> results = timetableRepository.findDistinctSubjectSectionCourseByFacultyId(facultyId);

    return results.stream().map(obj -> {
        Subject subject = (Subject) obj[0];
        SectionEnum section = (SectionEnum) obj[1];
        String courseName = (String) obj[2];

        AssignedSubjectDTO dto = new AssignedSubjectDTO();
        dto.setSubjectId(subject.getId());
        dto.setSubjectName(subject.getName());
        dto.setSemester(subject.getSemester());
        dto.setSection(section);
        dto.setCourseName(courseName);

        return dto;
    }).toList();
   }

     @Override
     public FacultyDTO getFacultyByEmail(String email) {
         Faculty faculty = facultyRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Faculty not found with email: " + email));
        return mapToDTO(faculty);
    }
}
    

