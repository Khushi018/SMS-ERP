package com.aristiec.schoolmanagementsystem.serviceImpl.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamAttendanceCreateDto;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamAttendanceReadDto;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamMapper;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamAttendance;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamAttendanceRepository;
import com.aristiec.schoolmanagementsystem.service.exam.ExamAttendanceService;
import com.aristiec.schoolmanagementsystem.service.exam.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamAttendanceServiceImpl implements ExamAttendanceService {

    private final ExamAttendanceRepository examAttendanceRepository;
    private final StudentDetailsRepository studentDetailsRepository;
    private final ExamService examService;
    private final ExamMapper mapper;


    @Transactional
    public ExamAttendanceReadDto createExamAttendance(ExamAttendanceCreateDto createDto) {

        StudentDetails student = studentDetailsRepository.findById(createDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Exam exam = examService.getExamById(createDto.getExamId());

        ExamAttendance examAttendance = new ExamAttendance();
        examAttendance.setStudent(student);
        examAttendance.setExam(exam);
        examAttendance.setStatus(createDto.getStatus());

        examAttendance = examAttendanceRepository.save(examAttendance);

        return mapper.mapToReadDto(examAttendance);
    }

    public ExamAttendanceReadDto getExamAttendance(Long studentId, Long examId) {
        ExamAttendance attendance = examAttendanceRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new RuntimeException("No Entry found with these id's"));

        return mapper.mapToReadDto(attendance);
    }

    public List<ExamAttendanceReadDto> getExamAttendanceByExam(Long examId) {
        List<ExamAttendance> attendanceList = examAttendanceRepository.findByExamId(examId)
                .orElseThrow(() -> new RuntimeException("No Entry found with this id"));

        return attendanceList.stream()
                .map(mapper::mapToReadDto)
                .collect(Collectors.toList());
    }

    public List<ExamAttendanceReadDto> getExamAttendanceByStudent(Long studentId) {
        List<ExamAttendance> attendanceList = examAttendanceRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("No Entry found with this id"));

        return attendanceList.stream()
                .map(mapper::mapToReadDto)
                .collect(Collectors.toList());
    }

    public List<ExamAttendanceReadDto> getAllExamAttendances() {
        List<ExamAttendance> attendances = examAttendanceRepository.findAll();
        return attendances.stream()
                .map(mapper::mapToReadDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteExamAttendance(Long studentId, Long examId) {
        ExamAttendance attendance = examAttendanceRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new RuntimeException("No attendance found for the given student and exam"));
        examAttendanceRepository.delete(attendance);
    }

    @Override
    public List<ExamAttendanceReadDto> getStudentStatus(Long studentId, int sem) {
        List<ExamAttendance> attendances = examAttendanceRepository.getStudentStatus(studentId, sem, ExamStatusEnum.COMPLETED);
        return attendances.stream()
                .map(mapper::mapToReadDto)
                .collect(Collectors.toList());
    }
}

