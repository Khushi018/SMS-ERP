package com.aristiec.schoolmanagementsystem.serviceImpl.details;

import com.aristiec.schoolmanagementsystem.constant.constantsValue;
import com.aristiec.schoolmanagementsystem.constant.enums.LevelEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.dto.ParentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.SemesterPerformanceDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentCourseSubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.StudentDetailsResponseDto;
import com.aristiec.schoolmanagementsystem.dto.StudentListResponseDto;
import com.aristiec.schoolmanagementsystem.dto.SubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.timetable.TimeTableDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.AssignmentSubmission;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;
import com.aristiec.schoolmanagementsystem.modal.timetable.Timetable;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentRepository;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentSubmissionRepository;
import com.aristiec.schoolmanagementsystem.repository.attendance.AttendanceRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.MarksRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.HostelRepository;
import com.aristiec.schoolmanagementsystem.repository.timetable.TimetableRepository;
import com.aristiec.schoolmanagementsystem.service.details.StudentDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class StudentDetailsServiceImpl implements StudentDetailsService {

    private final HostelRepository hostelRepository;

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private AssignmentSubmissionRepository assignmentRepo;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private MarksRepository marksRepo;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CourseRepository courseRepository;

    StudentDetailsServiceImpl(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;       
     
    }

    @Override
    public StudentDetailsResponseDto admitStudent(StudentDetailsDto studentDetailsDto) {

        if (studentDetailsRepository.findByEmail(studentDetailsDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 1. Fetch course
        Course course = courseRepository.findById(
                studentDetailsDto.getCourse().getId()).orElseThrow(() -> new RuntimeException("Course not found"));

        // 2. Map DTO to entity
        StudentDetails mappedStudent = modelMapper.map(studentDetailsDto, StudentDetails.class);
        mappedStudent.setCourse(course);
        mappedStudent.setYear(String.valueOf(LocalDate.now().getYear()));

        // 3. Assign section dynamically
        SectionEnum assignedSection = findAvailableSection(course, studentDetailsDto.getYear());
        mappedStudent.setSection(assignedSection);

        mappedStudent.setCourse(course);
        mappedStudent.setSemester(studentDetailsDto.getSemester());


        if (mappedStudent.getParentDetails() != null) {
            mappedStudent.getParentDetails().setStudentDetails(mappedStudent);
        }
        if (mappedStudent.getAddress() != null) {
            mappedStudent.getAddress().forEach(addr -> addr.setStudentDetails(mappedStudent));
        }
        if (mappedStudent.getPreviousEducation() != null) {
            mappedStudent.getPreviousEducation().forEach(edu -> edu.setStudentDetails(mappedStudent));
        }

        // 5. Save once to generate ID
        StudentDetails savedStudent = studentDetailsRepository.save(mappedStudent);

        // 6. Generate student code using ID
        String generatedCode = generateStudentCode(course, savedStudent.getId());
        savedStudent.setStudentCode(generatedCode);

        // 7. Save again to persist the studentCode
        savedStudent = studentDetailsRepository.save(savedStudent);

        // 8. Return response DTO
        return modelMapper.map(savedStudent, StudentDetailsResponseDto.class);
    }


    @Override
        public StudentDetailsResponseDto updateAdmittedStudent(Long studentId, StudentDetailsDto studentDetailsDto) {
            StudentDetails existingStudent = studentDetailsRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student details not found for student ID: " + studentId));
    
            // Update fields from DTO
            modelMapper.map(studentDetailsDto, existingStudent);
    
            // If course is updated, fetch and set it
            if (studentDetailsDto.getCourse() != null && studentDetailsDto.getCourse().getId() != null) {
                Course course = courseRepository.findById(studentDetailsDto.getCourse().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                existingStudent.setCourse(course);
            }
    
            // If parent details are present, update their reference
            if (existingStudent.getParentDetails() != null) {
                existingStudent.getParentDetails().setStudentDetails(existingStudent);
            }
            if (existingStudent.getAddress() != null) {
                existingStudent.getAddress().forEach(addr -> addr.setStudentDetails(existingStudent));
            }
            if (existingStudent.getPreviousEducation() != null) {
                existingStudent.getPreviousEducation().forEach(edu -> edu.setStudentDetails(existingStudent));
            }
    
            StudentDetails updatedStudent = studentDetailsRepository.save(existingStudent);
            return modelMapper.map(updatedStudent, StudentDetailsResponseDto.class);
        }

    @Override
    public void deleteStudentById(Long studentId) {
        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Student details not found for student ID: " + studentId));
        studentDetailsRepository.delete(student);
        if (studentDetailsRepository.count() == 0) {
            studentDetailsRepository.resetAutoIncrement();
        }
    }

    @Override
    public ParentDetailsDto getParentDetailsByStudentId(Long studentId) {
        ParentDetails parentDetails = studentDetailsRepository.findParentDetailsByStudentId(studentId);

        if (parentDetails == null) {
            throw new ResourceNotFoundException("Parent details not found for student ID: " + studentId);
        }

        return modelMapper.map(parentDetails, ParentDetailsDto.class);
    }

    @Override
    public CourseDTO getCourseByStudentId(Long studentId) {
        Course course = studentDetailsRepository.findCourseByStudentId(studentId);
        if (course == null) {
            throw new ResourceNotFoundException("Course details not found for student ID: " + studentId);
        }
        return modelMapper.map(course, CourseDTO.class);
    }

    @Override
    public StudentDetailsResponseDto getStudentDetailsById(Long studentId) {
        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student details not found for student ID: " + studentId));
        return modelMapper.map(student, StudentDetailsResponseDto.class);
    }

    @Override
    public List<StudentDetailsResponseDto> getAllStudents() {
        return studentDetailsRepository.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentDetailsResponseDto.class))
                .toList();
    }

    private String generateStudentCode(Course course, Long studentId) {
        String courseCode = course.getCourseCode(); // e.g., "CS"
        String year = String.valueOf(LocalDate.now().getYear());
        return String.format("%s%s%03d", courseCode, year, studentId);
    }

    @Override
    public StudentDetailsResponseDto getStudentByCode(String studentCode) {
        StudentDetails student = studentDetailsRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Student not found with code: " + studentCode));
        return modelMapper.map(student, StudentDetailsResponseDto.class);
    }

    @Override
    public StudentDetailsResponseDto getStudentByEmail(String email) {
        StudentDetails student = studentDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
        return modelMapper.map(student, StudentDetailsResponseDto.class);
    }

    @Override
    public Page<StudentDetailsResponseDto> getAllStudents(Pageable pageable) {
        return studentDetailsRepository.findAll(pageable)
                .map(student -> modelMapper.map(student, StudentDetailsResponseDto.class));
    }

    @Override
    public List<StudentDetailsResponseDto> getStudentsByCourseAndYear(String courseCode, String year) {
        Course course = courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with code: " + courseCode));

        List<StudentDetails> students = studentDetailsRepository.findByCourseAndYear(course, year);
        if (students == null || students.isEmpty()) {
            throw new ResourceNotFoundException("No students found for course: " + courseCode + " and year: " + year);
        }

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDetailsResponseDto.class))
                .toList();
    }

    @Override
    public StudentListResponseDto getAllwithFilters(String courseCode, String year, String section, String level,
            String email, String mobileNumber, String aadharNumber, String gender, String firstName, String lastName) {
        Course course = null;
        SectionEnum sectionEnum = null;
        LevelEnum levelEnum = null;
        if (courseCode != null && !courseCode.isBlank()) {
            course = courseRepository.findByCourseCode(courseCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with code: " + courseCode));
        }

        if (section != null && !section.isBlank()) {
            try {
                sectionEnum = SectionEnum.valueOf(section.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Invalid section: " + section);
            }
        }
        if (level != null && !level.isBlank()) {
            try {
                levelEnum = LevelEnum.valueOf(level.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Invalid levelEnum: " + levelEnum);
            }
        }

        Specification<StudentDetails> spec = Specification
                .where(StudentSpecifications.hasCourse(course))
                .and(StudentSpecifications.hasYear(year))
                .and(StudentSpecifications.hasSection(sectionEnum))
                .and(StudentSpecifications.hasLevel(levelEnum))
                .and(StudentSpecifications.hasEmail(email))
                .and(StudentSpecifications.hasMobileNumber(mobileNumber))
                .and(StudentSpecifications.hasAadharNumber(aadharNumber))
                .and(StudentSpecifications.hasGender(gender))
                .and(StudentSpecifications.hasFirstName(firstName))
                .and(StudentSpecifications.hasLastName(lastName));

        List<StudentDetails> students = studentDetailsRepository.findAll(spec);

        if (students == null || students.isEmpty()) {
            throw new ResourceNotFoundException("No students found with the provided filters.");
        }
        List<StudentDetailsResponseDto> dtoList = students.stream()
                .map(student -> modelMapper.map(student, StudentDetailsResponseDto.class))
                .toList();

        return new StudentListResponseDto(dtoList.size(), dtoList);
    }

    @Override
    public SectionEnum findAvailableSection(Course course, String year) {
        for (SectionEnum section : SectionEnum.values()) {
            long count = studentDetailsRepository.countBySectionAndCourseAndYear(section, course, year);
            if (count < constantsValue.SECTION_CAPACITY) {
                return section;
            }
        }
        throw new ResourceNotFoundException("All sections are full. Cannot admit more students.");
    }

    @Override
    public StudentCourseSubjectMinimalDTO getStudentCourseAndSubjectNames(Long studentId) {

        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StringBuilder fullName = new StringBuilder();
        if (student.getFirstName() != null)
            fullName.append(student.getFirstName()).append("null");
        if (student.getMiddleName() != null)
            fullName.append(student.getMiddleName()).append("null");
        if (student.getLastName() != null)
            fullName.append(student.getLastName());

        String studentName = fullName.toString().trim();

        String studentCode = student.getStudentCode();
        Course course = student.getCourse();

        String courseName = course.getCourseName();
        String courseCode = course.getCourseCode();

        List<SubjectMinimalDTO> subjects = course.getSubjects().stream()
                .map(subject -> new SubjectMinimalDTO(subject.getName(), subject.getCode(), subject.getCredit(),
                        subject.getSemester()))
                .toList();

        return new StudentCourseSubjectMinimalDTO(studentName, studentCode, courseName, courseCode, subjects);

    }

 
}
