package com.aristiec.schoolmanagementsystem.serviceImpl.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamMapper;
import com.aristiec.schoolmanagementsystem.dto.exam.StudentResultDTO;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamAttendanceRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.MarksRepository;
import com.aristiec.schoolmanagementsystem.service.exam.ExamResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class ExamResultsServiceImpl implements ExamResultsService {

    @Autowired
    private MarksRepository marksRepository;
    @Autowired
    private ExamAttendanceRepository examAttendanceRepository;
    @Autowired
    private ExamMapper mapper;

    @Override
    public List<StudentResultDTO> getStudentResults(Long studentId, Integer semester, ExamTypeEnum examType) {
        List<Marks> marksList = marksRepository.findStudentResults(studentId, semester, examType);
        return marksList.stream()
                .map(m -> new StudentResultDTO(
                        m.getExam().getExamName(),
                        m.getExam().getSubject().getName(),  // Subject name
                        m.getExam().getDateTime(),
                        m.getExam().getDuration(),
                        m.getMarksObtained(),
                        m.getGrade()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamDTO> getExamsAbsentForStudent(Long studentId, int sem) {
        List<Exam> absentExams = examAttendanceRepository.findExamsByStudentIdAndStatusAbsentAndSemester(studentId, sem);
        return absentExams.stream()
                .map(mapper::toDto)
                .toList();
    }
}
