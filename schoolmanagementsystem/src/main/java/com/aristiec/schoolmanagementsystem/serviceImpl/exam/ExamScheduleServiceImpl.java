package com.aristiec.schoolmanagementsystem.serviceImpl.exam;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamScheduleRepository;
import com.aristiec.schoolmanagementsystem.service.exam.ExamScheduleService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamScheduleServiceImpl implements ExamScheduleService {

    @Autowired
    private final ExamScheduleRepository examScheduleRepository;
    private final CourseRepository courseRepository;
    private final ExamServiceImpl examService;

    @Override
    public ExamSchedule getScheduleById(Long id) {
        return examScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam Schedule not found"));
    }

    @Override
    public List<ExamSchedule> getAllSchedules() {
        return examScheduleRepository.findAll();
    }

    @Override
    public ExamSchedule createExamSchedule(ExamSchedule examSchedule) {
        Course course = courseRepository.findById(examSchedule.getCourse().getId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        examSchedule.setCourse(course);
        return examScheduleRepository.save(examSchedule);
    }

    @Override
    public int getExamCount(Long id) {
        return getScheduleById(id).getCount();
    }

    @Override
    public List<LocalDateTime> getExamDates(Long id) {
        return getScheduleById(id).getDateTimeList();
    }

    @Override
    public void deleteExamSchedule(Long id) {

        List<Exam> exams = examService.getScheduleExams(id);
        for (Exam exam : exams) {
            examService.deleteById(exam.getExamId());
        }
        examScheduleRepository.deleteById(id);
    }
}
