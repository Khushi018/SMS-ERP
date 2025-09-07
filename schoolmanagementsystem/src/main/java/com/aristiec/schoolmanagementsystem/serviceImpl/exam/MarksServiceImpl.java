package com.aristiec.schoolmanagementsystem.serviceImpl.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.*;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectMarksDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.MarksRepository;
import com.aristiec.schoolmanagementsystem.service.exam.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarksServiceImpl implements MarksService {
    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentDetailsRepository studentRepository;

    public List<MarksReturnDto> getMarksForExam(long examId) {
        List<Marks> marksList = marksRepository.findAllMarksByExamId(examId);
        return marksList.stream()
                .map(ExamMapper::toMarksReturnDto)
                .toList();
    }

    public MarksReturnDto getMarksForStudentAndExam(Long studentId, long examId) {
        Marks marks = marksRepository.findMarksByStudentAndExam(studentId, examId);
        if (marks != null) {
            return ExamMapper.toMarksReturnDto(marks);
        } else {
            throw new RuntimeException("Marks not found for this student and exam");
        }
    }

    public MarksReturnDto saveMarksForStudent(Long studentId, long examId, int marksObtained, GradeEnum grade) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
        StudentDetails student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        Marks existingMarks = marksRepository.findMarksByStudentAndExam(studentId, examId);
        Marks marks;
        if (existingMarks != null) {
            // Update existing marks
            existingMarks.setMarksObtained(marksObtained);
            existingMarks.setGrade(grade);
            marks = marksRepository.save(existingMarks);
        } else {
            // Create new marks entry
            marks = new Marks();
            marks.setMarksObtained(marksObtained);
            marks.setExam(exam);
            marks.setStudent(student);
            marks.setGrade(grade);
            marks = marksRepository.save(marks);
        }

        return ExamMapper.toMarksReturnDto(marks);
    }

   

    @Override
    public List<Long> getStudentWithGrade(GradeEnum grade) {
        return marksRepository.findStudentWithGrade(grade);
    }

    @Override
    public void deleteMarksById(Long marksId) {
        marksRepository.findById(marksId).orElseThrow(() -> new RuntimeException("Marks not found"));
        marksRepository.deleteById(marksId);
    }

    @Override
    public MarksReturnDto updateMarksAndGrade(Long marksId, GradeEnum grade, int marksObtained) {
        Marks marks = marksRepository.findById(marksId).orElseThrow(() -> new RuntimeException("Marks not found"));

        marks.setMarksObtained(marksObtained);
        marks.setGrade(grade);
        Marks updatedMarks = marksRepository.save(marks);
        return ExamMapper.toMarksReturnDto(updatedMarks);
    }

    @Override
    public List<MarksReturnDto> getMarksForStudentAndExamSch(Long studentId, long schId) {
        List<Marks> marks = marksRepository.findStudentMarksWithSchId(studentId, schId);
        if (marks != null) {
            return marks.stream().map(ExamMapper::toMarksReturnDto).toList();
        } else {
            throw new RuntimeException("Marks not found for this student and exam schedule");
        }
    }
}
