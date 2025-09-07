package com.aristiec.schoolmanagementsystem.service.notification;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.notification.ExamNotificationRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.ExamNotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.NotificationResponseDTO;

public interface ExamNotificationService {
     ExamNotificationResponseDTO createNotification(ExamNotificationRequestDTO dto);
    List<ExamNotificationResponseDTO> getAllNotifications();
List<NotificationResponseDTO> getTitlesByCourseAndSemester(Long courseId, Integer semester);
}
