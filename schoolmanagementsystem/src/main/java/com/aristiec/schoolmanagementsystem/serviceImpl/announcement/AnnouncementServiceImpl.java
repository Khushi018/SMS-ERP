package com.aristiec.schoolmanagementsystem.serviceImpl.announcement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.announcement.AnnouncementRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.announcement.AnnouncementResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.announcement.StudentAnnouncementResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.announcement.Announcement;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.aristiec.schoolmanagementsystem.repository.announcement.AnnouncementRepository;
import com.aristiec.schoolmanagementsystem.repository.department.DepartmentRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.service.announcement.AnnouncementService;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    
       @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private  CourseRepository courseRepository;
     
    @Autowired
    private  StudentDetailsRepository studentRepository;
    @Autowired
    private  ModelMapper modelMapper;

   

    @Override
    public AnnouncementResponseDTO createAnnouncement(AnnouncementRequestDTO dto) {
       Course course = null;
    if (dto.getCourseId() != null) {
        course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    Announcement announcement = new Announcement();
    announcement.setTitle(dto.getTitle());
    announcement.setMessage(dto.getMessage());
    announcement.setType(dto.getType());
    announcement.setYear(dto.getYear());
    announcement.setCreatedBy(dto.getCreatedBy());
    announcement.setCourse(course);
    announcement.setCreatedAt(LocalDateTime.now());

    Announcement saved = announcementRepository.save(announcement);

    AnnouncementResponseDTO responseDTO = modelMapper.map(saved, AnnouncementResponseDTO.class);
    responseDTO.setCourseName(saved.getCourse() != null ? saved.getCourse().getCourseName() : null);

    return responseDTO;
    }

    @Override
    public List<StudentAnnouncementResponseDTO> getAnnouncementsForStudent(Long studentId) {
          StudentDetails student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course studentCourse = student.getCourse();
        String studentYear = student.getYear();

        return announcementRepository.findAll().stream()
            .filter(a ->
                    (a.getCourse() == null || a.getCourse().getId().equals(studentCourse.getId())) &&
                    (a.getYear() == null || a.getYear().equals(studentYear))
            )
            .map(a -> {
                StudentAnnouncementResponseDTO dto = new StudentAnnouncementResponseDTO();
                dto.setTitle(a.getTitle());
                dto.setMessage(a.getMessage());
                dto.setCreatedAt(a.getCreatedAt());
                dto.setPostedBy(a.getCreatedBy());
                dto.setType(a.getType());
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementResponseDTO> getAllAnnouncements() {
             return announcementRepository.findAll().stream()
                .map(announcement -> {
                    AnnouncementResponseDTO dto = modelMapper.map(announcement, AnnouncementResponseDTO.class);
                    if (announcement.getCourse() != null) {
                        dto.setCourseName(announcement.getCourse().getCourseName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AnnouncementResponseDTO getAnnouncementById(Long id) {
               Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        AnnouncementResponseDTO dto = modelMapper.map(announcement, AnnouncementResponseDTO.class);
        if (announcement.getCourse() != null) {
            dto.setCourseName(announcement.getCourse().getCourseName());
        }
        return dto;
    }

    @Override
    public AnnouncementResponseDTO updateAnnouncement(Long id, AnnouncementRequestDTO dto) {
          Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        Course course = null;
        if (dto.getCourseId() != null) {
            course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        }

        announcement.setTitle(dto.getTitle());
        announcement.setMessage(dto.getMessage());
        announcement.setType(dto.getType());
        announcement.setYear(dto.getYear());
        announcement.setCreatedBy(dto.getCreatedBy());
        announcement.setCourse(course);

        Announcement updated = announcementRepository.save(announcement);

        AnnouncementResponseDTO responseDTO = modelMapper.map(updated, AnnouncementResponseDTO.class);
        responseDTO.setCourseName(course != null ? course.getCourseName() : null);

        return responseDTO;
    }

    @Override
    public void deleteAnnouncement(Long id) {
       Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        announcementRepository.delete(announcement);
    }
    
}

    

