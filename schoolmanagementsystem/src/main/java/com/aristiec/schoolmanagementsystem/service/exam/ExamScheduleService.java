package com.aristiec.schoolmanagementsystem.service.exam;

import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;

import java.time.LocalDateTime;
import java.util.List;


public interface ExamScheduleService {
    ExamSchedule getScheduleById(Long id);

    List<ExamSchedule> getAllSchedules();

    //List<Exam> getExamsForSchedule(Long scheduleId);

    ExamSchedule createExamSchedule(ExamSchedule examSchedule);

    int getExamCount(Long id);

    List<LocalDateTime> getExamDates(Long id);

    void deleteExamSchedule(Long id);
}
