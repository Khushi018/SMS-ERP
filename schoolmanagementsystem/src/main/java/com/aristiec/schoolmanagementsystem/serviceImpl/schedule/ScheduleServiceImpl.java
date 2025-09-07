package com.aristiec.schoolmanagementsystem.serviceImpl.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.schedule.UpcomingScheduleDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.OnlineExaminationDto;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.AssiDTO;
import com.aristiec.schoolmanagementsystem.modal.assignment.Assignment;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.OnlineExamination;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamRepository;
import com.aristiec.schoolmanagementsystem.repository.onlineexam.OnlineExaminationRepository;
import com.aristiec.schoolmanagementsystem.service.schedule.ScheduleService;

import org.modelmapper.ModelMapper;

import com.aristiec.schoolmanagementsystem.constant.enums.AssignmentStatusEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final AssignmentRepository assignmentRepository;
    private final ExamRepository examRepository;
    private final OnlineExaminationRepository onlineExamRepository;
    private final ModelMapper mapper;

    @Override
    public UpcomingScheduleDTO getUpcomingSchedule(LocalDate date) {
        // ✅ 1. Assignments
        List<AssiDTO> assignments = assignmentRepository.findAll().stream()
                .filter(a -> a.getDueDate() != null && a.getDueDate().isAfter(date))
                .map(a -> {
                    AssiDTO dto = new AssiDTO();
                    dto.setId(a.getId());
                    dto.setTitle(a.getTitle());
                    dto.setDescription(a.getDescription());
                    dto.setDueDate(a.getDueDate().toString());
                    dto.setAttempted(Boolean.TRUE.equals(a.getAttempted()));
                    dto.setSubmissionMode(a.getSubmissionMode());
                    dto.setSubjectName(a.getSubject() != null ? a.getSubject().getName() : null);

                    // Dynamically computed status
                    AssignmentStatusEnum status;
                    if (Boolean.TRUE.equals(a.getAttempted())) {
                        status = AssignmentStatusEnum.SUBMITTED;
                    } else if (a.getDueDate().isBefore(LocalDate.now())) {
                        status = AssignmentStatusEnum.OVERDUE;
                    } else {
                        status = AssignmentStatusEnum.PENDING;
                    }
                    dto.setStatus(status);

                    return dto;
                })
                .collect(Collectors.toList());

        // ✅ 2. Offline Exams
        List<ExamDTO> offlineExams = examRepository.findAll().stream()
                .filter(e -> e.getDateTime() != null && e.getDateTime().toLocalDate().isAfter(date))
                .map(e -> {
                    ExamDTO dto = new ExamDTO();
                    dto.setExamId(e.getExamId());
                    dto.setExamName(e.getExamName());
                    dto.setDateTime(e.getDateTime());
                    dto.setDuration(e.getDuration());
                    dto.setMarks(e.getMarks());
                    dto.setStatus(e.getStatus());
                    dto.setExamScheduleId(e.getExamSchedule().getExamScheduleId());
                    dto.setSubjectId(e.getSubject().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        // ✅ 3. Online Exams
        List<OnlineExaminationDto> onlineExams = onlineExamRepository.findAll().stream()
                .filter(oe -> oe.getStartTime() != null &&
                        (oe.getStartTime().toLocalDate().isEqual(date)
                                || oe.getStartTime().toLocalDate().isAfter(date)))

                .map(oe -> {
                    OnlineExaminationDto dto = mapper.map(oe, OnlineExaminationDto.class);
                    dto.setContents(null); // or Collections.emptyList() if you prefer
                    return dto;
                })
                .collect(Collectors.toList());

        // ✅ Return Combined DTO
        return new UpcomingScheduleDTO(assignments, offlineExams, onlineExams);
    }
}
