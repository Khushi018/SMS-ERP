package com.aristiec.schoolmanagementsystem.repository.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.notification.ExamNotification;

@Repository
public interface ExamNotificationRepository  extends JpaRepository<ExamNotification, Long>{
     List<ExamNotification> findByCourse_Id(Long courseId);
    List<ExamNotification> findBySemester(Integer semester);
    List<ExamNotification> findByCourse_IdAndSemester(Long courseId, Integer semester);
}
