package com.aristiec.schoolmanagementsystem.serviceImpl.notification;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.notification.ExamNotificationRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.ExamNotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.NotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.notification.ExamNotification;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.notification.ExamNotificationRepository;
import com.aristiec.schoolmanagementsystem.service.notification.ExamNotificationService;

@Service
public class ExamNotificationServiceImpl  implements ExamNotificationService{
      
       @Autowired
    private ExamNotificationRepository notificationRepo;

    @Autowired
    private CourseRepository courseRepo;
      @Autowired
     private  StudentDetailsRepository studentDetailsRepository;
     @Autowired
    private ModelMapper modelMapper;


    @Override
    public ExamNotificationResponseDTO createNotification(ExamNotificationRequestDTO dto) {
        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        ExamNotification notification = new ExamNotification();
        notification.setTitle(dto.getTitle());
        notification.setDescription(dto.getDescription());
        notification.setSemester(dto.getSemester());
        notification.setCourse(course);

        ExamNotification saved = notificationRepo.save(notification);
       
        return modelMapper.map(saved, ExamNotificationResponseDTO.class);
    }

    @Override
    public List<ExamNotificationResponseDTO> getAllNotifications() {
       return notificationRepo.findAll().stream()
            .map(notification -> modelMapper.map(notification, ExamNotificationResponseDTO.class))
            .toList();
     }
   

    @Override
    public List<NotificationResponseDTO> getTitlesByCourseAndSemester(Long courseId, Integer semester) {
           return notificationRepo.findByCourse_IdAndSemester(courseId, semester)
            .stream()
            .map(notification -> {
                NotificationResponseDTO dto = new NotificationResponseDTO();
                dto.setTitle(notification.getTitle());
                dto.setDescription(notification.getDescription());
                dto.setCreatedAt(notification.getCreatedAt());
                return dto;
            })
            .toList();
    }


}
