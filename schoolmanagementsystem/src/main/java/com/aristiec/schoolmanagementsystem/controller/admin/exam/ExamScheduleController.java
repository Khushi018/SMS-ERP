package com.aristiec.schoolmanagementsystem.controller.admin.exam;

import com.aristiec.schoolmanagementsystem.dto.exam.ExamScheduleCreateDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamScheduleDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.service.exam.ExamScheduleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/exam-schedule")

@SecurityRequirement(name = "bearerAuth") 
@RequiredArgsConstructor
@Tag(name = "Exam Schedule Management", description = "APIs for managing exam schedules in the school management system")
public class ExamScheduleController {

    private final ExamScheduleService examScheduleService;
    private final CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<ExamScheduleDTO> createExamSchedule(@RequestBody ExamScheduleCreateDTO examScheduleCreateDTO) {

        ExamSchedule examS = new ExamSchedule();
        Course course = courseRepository.findById(examScheduleCreateDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id"));
        examS.setCourse(course);
        examS.setDescription(examScheduleCreateDTO.getDescription());
        examS.setStartDate(examScheduleCreateDTO.getStartDate());
        examS.setExamType(examScheduleCreateDTO.getExamType());
        examS.setSem(examScheduleCreateDTO.getSem());

        ExamScheduleDTO examScheduleDTO = new ExamScheduleDTO();
        ExamSchedule examSch = examScheduleService.createExamSchedule(examS);

        //did not add mapper as minimal usage
        examScheduleDTO.setSem(examSch.getSem());
        examScheduleDTO.setExamType(examSch.getExamType());
        examScheduleDTO.setExamScheduleId(examSch.getExamScheduleId());
        examScheduleDTO.setStartDate(examSch.getStartDate());
        examScheduleDTO.setDescription(examSch.getDescription());
        examScheduleDTO.setCount(examSch.getCount());
        examScheduleDTO.setCourseId(examSch.getCourse().getId());
        examScheduleDTO.setLocalDateTimes(examSch.getDateTimeList());
        return new ResponseEntity<>(examScheduleDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamScheduleDTO> getSchedule(@PathVariable Long id) {
        ExamSchedule schedule = examScheduleService.getScheduleById(id);
        ExamScheduleDTO dto = new ExamScheduleDTO();
        dto.setSem(schedule.getSem());
        dto.setExamType(schedule.getExamType());
        dto.setExamScheduleId(schedule.getExamScheduleId());
        dto.setStartDate(schedule.getStartDate());
        dto.setDescription(schedule.getDescription());
        dto.setCount(schedule.getCount());
        dto.setCourseId(schedule.getCourse().getId());
        dto.setLocalDateTimes(schedule.getDateTimeList());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<ExamScheduleDTO> getAllSchedules() {
        List<ExamSchedule> schedules = examScheduleService.getAllSchedules();
        List<ExamScheduleDTO> dtoList = schedules.stream().map(schedule -> {
            ExamScheduleDTO dto = new ExamScheduleDTO();
            dto.setSem(schedule.getSem());
            dto.setExamType(schedule.getExamType());
            dto.setExamScheduleId(schedule.getExamScheduleId());
            dto.setStartDate(schedule.getStartDate());
            dto.setDescription(schedule.getDescription());
            dto.setCount(schedule.getCount());
            dto.setCourseId(schedule.getCourse().getId());
            dto.setLocalDateTimes(schedule.getDateTimeList());
            return dto;
        }).collect(Collectors.toList());

        return dtoList;
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> getExamCount(@PathVariable Long id){
        return new ResponseEntity<>(examScheduleService.getExamCount(id), HttpStatus.OK);
    }

    @GetMapping("/dates/{id}")
    public ResponseEntity<List<LocalDateTime>> getExamDates(@PathVariable Long id){
        return new ResponseEntity<>(examScheduleService.getExamDates(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExamSchedule(@PathVariable Long id){
        examScheduleService.deleteExamSchedule(id);
        return ResponseEntity.ok("Success");
    }
}
