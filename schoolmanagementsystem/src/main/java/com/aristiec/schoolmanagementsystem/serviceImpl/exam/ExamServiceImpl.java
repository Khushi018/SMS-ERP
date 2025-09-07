package com.aristiec.schoolmanagementsystem.serviceImpl.exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamAttendance;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamAttendanceRepository;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamHistoryDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamMapper;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;
import com.aristiec.schoolmanagementsystem.repository.attendance.AttendanceRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamScheduleRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.MarksRepository;
import com.aristiec.schoolmanagementsystem.service.exam.ExamService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ExamScheduleRepository examScheduleRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudentDetailsRepository studentRepository;
    private final MarksRepository marksRepository;
    private final ExamMapper examMapper;
    private final ExamAttendanceRepository examAttendanceRepository;

    @Override
    public Exam createExam(Exam exam) {
        ExamSchedule schedule = examScheduleRepository.findById(exam.getExamSchedule().getExamScheduleId())
                .orElseThrow(() -> new EntityNotFoundException("ExamSchedule not found"));

        exam.setExamSchedule(schedule);
        schedule.setCount(schedule.getCount() + 1);
        schedule.getDateTimeList().add(exam.getDateTime());
        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExamById(long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with this id"));
    }

    @Override
    public void deleteById(long id) {
        Optional<Exam> exam = examRepository.findById(id);
        if (exam.isPresent()) {
            ExamSchedule schedule = examScheduleRepository.findById(exam.get().getExamSchedule().getExamScheduleId())
                    .orElseThrow(() -> new EntityNotFoundException("ExamSchedule not found"));
            schedule.setCount(schedule.getCount() - 1);
            schedule.getDateTimeList().remove(exam.get().getDateTime());
            examRepository.deleteById(id);
        }
    }

    @Override
    public List<Exam> getScheduleExams(Long id) {
        return examRepository.findByExamSchedule(id);
    }

    @Override
    public List<Exam> getExamsByCourseId(Long courseId) {
        return examRepository.findExamsByCourseId(courseId);
    }

    @Override
    public List<Exam> getExamsByExamType(ExamTypeEnum examType) {
        return examRepository.findByExamType(examType);
    }

    @Override
    public List<ExamDTO> getExamsByStudentId(Long studentId) {
        Course course = courseRepository.findCourseByStudentId(studentId);
        return examRepository.findExamsByCourseId(course.getId())
                .stream()
                .map(examMapper::toDto)
                .toList();
    }

    @Override
    public List<ExamDTO> getExamsByStudentIdAndType(Long studentId, ExamTypeEnum examType) {
        Course course = courseRepository.findCourseByStudentId(studentId);
        List<Exam> exams = examRepository.findByExamTypeAndCourseId(examType, course.getId());
        return exams.stream().map(examMapper::toDto).toList();
    }

    public List<ExamHistoryDTO> getExamHistory(Long studentId) {
        StudentDetails student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Marks> marksList = marksRepository.findByStudentId(studentId);
        List<Attendance> attendanceList = attendanceRepository.findByStudentDetailsId(studentId);

        return marksList.stream().map(m -> {
        Exam exam = m.getExam();
        String subjectName = exam.getSubject().getName();
        LocalDate examDate = exam.getDateTime().toLocalDate();

        AttendanceStatus status = AttendanceStatus.ABSENT;

        // Try to find matching attendance on exam subject and date
        for (Attendance att : attendanceList) {
            if (att.getTimetable().getSubject().getName().equals(subjectName)
                    && att.getDate().equals(examDate)) {
                status = att.getAttendanceStatus();
                break;
            }
        }


            ExamHistoryDTO dto = new ExamHistoryDTO();
            dto.setSubject(exam.getSubject().getName());
            //dto.setExamType(exam.getExamType().name());
            dto.setDateTime(exam.getDateTime());
            dto.setMarksObtained(m.getMarksObtained());
            dto.setGrade(m.getGrade());
            dto.setAttendanceStatus(status);

            return dto;
        }).toList();
    }

    @Override
    public List<Exam> getExamsByExamStatus(ExamStatusEnum examStatus) {
        return examRepository.findByStatus(examStatus);
    }

    @Override
    public List<Exam> getExamsByScheduleIdAndType(Long scheduleId, ExamTypeEnum examType) {
        return examRepository.findByScheduleIdAndExamType(scheduleId, examType);
    }

    @Override
    public ExamDTO updateExamStatus(long examId, ExamStatusEnum status) {
        Optional<Exam> optionalExam = examRepository.findById(examId);

        if (optionalExam.isPresent()) {
            Exam exam = optionalExam.get();
            exam.setStatus(status);
            Exam updatedExam = examRepository.save(exam);
            return examMapper.toDto(updatedExam);
        }

        return null;
    }

    @Override
    public List<ExamDTO> getUpcomingExams() {

        List<Exam> upcomingExams = examRepository.findByStatus(ExamStatusEnum.UPCOMING);

        return upcomingExams.stream()
                .map(examMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamDTO> getCompletedExams() {

        List<Exam> completedExams = examRepository.findByStatus(ExamStatusEnum.COMPLETED);

        return completedExams.stream()
                .map(examMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean markExamAsCompletedAndStudentsPresent(Long examId, List<Long> studentIds) {
        // Find the exam by its ID
        Optional<Exam> examOpt = examRepository.findById(examId);
        if (examOpt.isEmpty()) {
            return false;
        }
        Exam exam = examOpt.get();

        exam.setStatus(ExamStatusEnum.COMPLETED);
        examRepository.save(exam);

        for (Long studentId : studentIds) {
            Optional<StudentDetails> studentOpt = studentRepository.findById(studentId);
            if (studentOpt.isPresent()) {
                StudentDetails student = studentOpt.get();

                Optional<ExamAttendance> attendanceOpt = examAttendanceRepository.findByStudentIdAndExamId(student.getId(), exam.getExamId());
                if (attendanceOpt.isPresent()) {
                    ExamAttendance attendance = attendanceOpt.get();
                    attendance.setStatus(AttendanceStatus.PRESENT);
                    examAttendanceRepository.save(attendance);
                } else {
                    ExamAttendance attendance = new ExamAttendance();
                    attendance.setExam(exam);
                    attendance.setStudent(student);
                    attendance.setStatus(AttendanceStatus.PRESENT);
                    examAttendanceRepository.save(attendance);
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public List<ExamDTO> updateExamStatusIfExpired() {
        List<Exam> exams = examRepository.findByStatus(ExamStatusEnum.UPCOMING);

        List<Exam> updatedExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (LocalDateTime.now().isAfter(exam.getDateTime())) {
                exam.setStatus(ExamStatusEnum.COMPLETED);
                updatedExams.add(exam);
                examRepository.save(exam);
            }
        }
        return updatedExams.stream()
                .map(examMapper::toDto)
                .collect(Collectors.toList());
    }
}
