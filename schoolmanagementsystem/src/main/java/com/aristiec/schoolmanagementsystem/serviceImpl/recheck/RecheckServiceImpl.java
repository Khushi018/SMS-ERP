package com.aristiec.schoolmanagementsystem.serviceImpl.recheck;

import com.aristiec.schoolmanagementsystem.constant.enums.RecheckEnum;
import com.aristiec.schoolmanagementsystem.dto.recheck.RecheckCreateDto;
import com.aristiec.schoolmanagementsystem.dto.recheck.RecheckDto;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.recheck.Recheck;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamRepository;
import com.aristiec.schoolmanagementsystem.repository.recheck.RecheckRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.service.recheck.RecheckService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecheckServiceImpl implements RecheckService {
    @Autowired
    private RecheckRepository recheckRepository;

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRepository examRepository;

    @Override
    public RecheckDto createRecheck(RecheckCreateDto recheckCreateDto, byte[] proofBytes) {
        // Fetch related entities (Student, Subject, Exam)
        StudentDetails studentDetails = studentDetailsRepository.findById(recheckCreateDto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + recheckCreateDto.getStudentId() + " not found"));

        Subject subject = subjectRepository.findById(recheckCreateDto.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject with ID " + recheckCreateDto.getStudentId() + " not found"));
        Exam exam = examRepository.findById(recheckCreateDto.getExamId())
                .orElseThrow(() -> new EntityNotFoundException("Exam with ID " + recheckCreateDto.getStudentId() + " not found"));
        // Create and save Recheck
        Recheck recheck = new Recheck();
        recheck.setStudent(studentDetails);
        recheck.setSubject(subject);
        recheck.setExam(exam);
        recheck.setOriginalScore(recheckCreateDto.getOriginalScore());
        recheck.setExpectedScore(recheckCreateDto.getExpectedScore());
        recheck.setRecheckReason(recheckCreateDto.getRecheckReason());
        recheck.setProof(proofBytes);
        recheck = recheckRepository.save(recheck);

        return toRecheckDto(recheck);
    }

    @Override
    public RecheckDto getRecheckById(Long id) {
        Recheck recheck = recheckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recheck Request with ID " + id + " not found"));
        return toRecheckDto(recheck);
    }

    @Override
    public List<RecheckDto> getRechecksByStudent(Long studentId) {
        List<Recheck> rechecks = recheckRepository.findByStudentId(studentId);
        return rechecks.stream().map(this::toRecheckDto).collect(Collectors.toList());
    }

    @Override
    public RecheckDto updateRecheckStatus(Long id, RecheckEnum status) {
        Recheck recheck = recheckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recheck Request with ID " + id + " not found"));
        recheck.setStatus(status);
        recheck.setRemarks(status == RecheckEnum.ACCEPTED ? "Approved" : "Rejected");
        recheck = recheckRepository.save(recheck);
        return toRecheckDto(recheck);
    }

    @Override
    public List<RecheckDto> getPendingRechecks() {
        List<Recheck> rechecks = recheckRepository.findByStatus(RecheckEnum.PENDING);
        return rechecks.stream().map(this::toRecheckDto).collect(Collectors.toList());
    }

    @Override
    public List<RecheckDto> getRechecksByExamId(Long examId) {

        List<Recheck> rechecks = recheckRepository.findByExamId(examId);

        return rechecks.stream().map(this::toRecheckDto).collect(Collectors.toList());
    }

    @Override
    public List<RecheckDto> getRechecksByStatus(RecheckEnum status) {
        List<Recheck> rechecks = recheckRepository.findByStatus(status);
        return rechecks.stream().map(this::toRecheckDto).collect(Collectors.toList());
    }


    public Map<String, Long> getStudentRecheckSummary(Long studentId) {
        Map<String, Long> summary = new HashMap<>();

        long acceptedCount = recheckRepository.countByStudentIdAndStatus(studentId, RecheckEnum.ACCEPTED);
        long rejectedCount = recheckRepository.countByStudentIdAndStatus(studentId, RecheckEnum.REJECTED);
        long pendingCount = recheckRepository.countByStudentIdAndStatus(studentId, RecheckEnum.PENDING);

        summary.put("accepted", acceptedCount);
        summary.put("rejected", rejectedCount);
        summary.put("pending", pendingCount);

        return summary;
    }

    @Override
    public void deleteRecheckById(Long id) {
        if (!recheckRepository.existsById(id)) {
            throw new RuntimeException("Recheck not found");
        }
        recheckRepository.deleteById(id);
    }

    @Override
    public void deleteRechecksByStudentId(Long studentId) {
        recheckRepository.deleteByStudentId(studentId);
    }

    @Override
    public void deleteRechecksByExamId(Long examId) {
        recheckRepository.deleteByExamId(examId);
    }

    @Override
    public byte[] getRecheckProof(Long recheckId) {
        Recheck recheck = recheckRepository.findById(recheckId)
                .orElseThrow(() -> new EntityNotFoundException("Recheck with ID " + recheckId + " not found"));

        return recheck.getProof();
    }

    // Mapper function
    private RecheckDto toRecheckDto(Recheck recheck) {
        return new RecheckDto(
                recheck.getId(),
                recheck.getStudent().getStudentCode(),
                recheck.getSubject().getName(),
                recheck.getExam().getExamName(),
                recheck.getOriginalScore(),
                recheck.getExpectedScore(),
                recheck.getRecheckReason(),
                recheck.getStatus().name(),
                recheck.getRemarks(),
                recheck.getRecheckFee()
        );
    }
}
