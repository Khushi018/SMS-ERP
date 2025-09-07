package com.aristiec.schoolmanagementsystem.serviceImpl.subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.FacultyMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.SubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.SubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultyDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultySubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.StudentMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.StudentSubjectsDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.SubjectFacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.repository.timetable.TimetableRepository;
import com.aristiec.schoolmanagementsystem.service.subject.SubjectService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.FileAlreadyExistsException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final Path uploadDir = Paths.get("uploads/subjects");

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @Autowired
    private SubjectFacultyRepository facultyRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubjectDTO createSubject(SubjectDTO dto) {
        Subject newSubject = modelMapper.map(dto, Subject.class);

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id:" + dto.getCourseId()));

        newSubject.setCourse(course);

        Subject saved = subjectRepository.save(newSubject);

        SubjectDTO result = modelMapper.map(saved, SubjectDTO.class);
        result.setCourseId(course.getId());

        return result;

    }

    @Override
    public SubjectDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
        dto.setCourseId(subject.getCourse().getId());
        return dto;

    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(subject -> {
                    SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
                    dto.setCourseId(subject.getCourse().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        existingSubject.setName(subjectDTO.getName());
        existingSubject.setCode(subjectDTO.getCode());
        existingSubject.setCredit(subjectDTO.getCredit());
        existingSubject
                .setActive(subjectDTO.getActive() != null ? subjectDTO.getActive() : existingSubject.getActive());

        if (subjectDTO.getCourseId() != null) {
            Course course = courseRepository.findById(subjectDTO.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found with id: " + subjectDTO.getCourseId()));
            existingSubject.setCourse(course);
        }

        Subject updatedSubject = subjectRepository.save(existingSubject);

        SubjectDTO result = modelMapper.map(updatedSubject, SubjectDTO.class);
        result.setCourseId(updatedSubject.getCourse().getId());

        return result;
    }

    @Override
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }

    @Override
    public List<SubjectMinimalDTO> getSubjectsByStudentCourseAndSemester(Long studentId, Long courseId,
            Integer semester) {
        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = student.getCourse();

        if (!course.getId().equals(courseId)) {
            throw new RuntimeException("Course ID does not match student's course");
        }

        return course.getSubjects().stream()
                .filter(subject -> subject.getSemester() != null && subject.getSemester().equals(semester))
                .map(subject -> new SubjectMinimalDTO(subject.getName(), subject.getCode(), subject.getCredit(),
                        subject.getSemester()))
                .toList();
    }


    @Override
    public List<FacultySubjectDTO> getFacultiesBySubjectIdAndSection(Long subjectId, SectionEnum section) {

        List<Object[]> resultList = timetableRepository.findFacultyAndSectionBySubjectIdAndSection(subjectId, section);

        Set<FacultySubjectDTO> uniqueFacultySet = resultList.stream()
                .map(obj -> {
                    Faculty faculty = (Faculty) obj[0];
                    SectionEnum sec = (SectionEnum) obj[1];

                    return new FacultySubjectDTO(
                            faculty.getId(),
                            faculty.getFullName(),
                            faculty.getEmail(),
                            faculty.getPosition(),
                            sec);
                })
                .collect(Collectors.toSet());

        return new ArrayList<>(uniqueFacultySet);

    }
    @Override
    public void uploadSubjectFile(Long subjectId, MultipartFile file) {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = subjectId + "_" + file.getOriginalFilename();
            Path targetLocation = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource downloadSubjectFile(Long subjectId, String filename) {
        try {
            Path filePath = uploadDir.resolve(subjectId + "_" + filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }

}
