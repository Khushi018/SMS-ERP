package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamAttendance;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {


    private final ModelMapper modelMapper;

    @Autowired
    public ExamMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public ExamDTO toDto(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setExamId(exam.getExamId());
        dto.setExamName(exam.getExamName());
//        dto.setExamType(exam.getExamType().name());
        dto.setSubjectId(exam.getSubject().getId());
        dto.setDateTime(exam.getDateTime());
        dto.setDuration(exam.getDuration());
        dto.setMarks(exam.getMarks());
        dto.setExamScheduleId(exam.getExamSchedule().getExamScheduleId());
        dto.setStatus(exam.getStatus());
//        return modelMapper.map(exam, ExamDTO.class);
        return dto;
    }

    public Exam toEntity(ExamCreateDto dto, ExamSchedule examSchedule, Subject sub) {
//        Exam exam = modelMapper.map(dto, Exam.class);
//        exam.setExamSchedule(examSchedule);
//        exam.setSubject(sub);
        Exam exam = new Exam();
        exam.setExamName(dto.getExamName());
//        exam.setExamType(dto.getExamType());
        exam.setSubject(sub);
        exam.setDateTime(dto.getDateTime());
        exam.setDuration(dto.getDuration());
        exam.setExamSchedule(examSchedule);
        exam.setMarks(dto.getMarks());
        exam.setStatus(dto.getStatus());
        return exam;
    }

    public static MarksDTO toMarksDto(Marks marks) {
        MarksDTO dto = new MarksDTO();
        dto.setStudentId(marks.getStudent().getId());
        dto.setExamId(marks.getExam().getExamId());
        dto.setMarksObtained(marks.getMarksObtained());
        dto.setGrade(marks.getGrade());
        return dto;
    }

    public static MarksReturnDto toMarksReturnDto(Marks marks) {
        MarksReturnDto dto = new MarksReturnDto();
        dto.setStudentId(marks.getStudent().getId());
        dto.setExamId(marks.getExam().getExamId());
        dto.setMarksObtained(marks.getMarksObtained());
        dto.setGrade(marks.getGrade());
        dto.setMarksId(marks.getMarksId());
        dto.setSubjectName(marks.getExam().getSubject().getName());
        dto.setTotalMarks(marks.getExam().getMarks());
        return dto;
    }

    public static Marks toMarksEntity(MarksDTO dto, StudentDetails student, Exam exam) {
        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setExam(exam);
        marks.setMarksObtained(dto.getMarksObtained());
        marks.setGrade(dto.getGrade());
        return marks;
    }

    public ExamAttendanceReadDto mapToReadDto(ExamAttendance examAttendance) {
        ExamAttendanceReadDto dto = new ExamAttendanceReadDto();
        dto.setId(examAttendance.getId());
        dto.setStudentId(examAttendance.getStudent().getId());
        dto.setExamId(examAttendance.getExam().getExamId());
        dto.setStatus(examAttendance.getStatus());
        return dto;
    }
}
