package com.aristiec.schoolmanagementsystem.serviceImpl.assignment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.AssignmentStatusEnum;
import com.aristiec.schoolmanagementsystem.dto.AssignmentDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSubmissionStatsDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.AssiDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.SubjectAssiDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Assignment;
import com.aristiec.schoolmanagementsystem.modal.assignment.AssignmentSubmission;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentRepository;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentSubmissionRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.service.assignment.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AssiDTO getAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        AssiDTO responseDTO = modelMapper.map(assignment, AssiDTO.class);

        responseDTO.setSubjectName(assignment.getSubject().getName());
        responseDTO.setStatus(AssignmentStatusEnum.PENDING);

        return responseDTO;

    }

    @Override
    public AssiDTO updateAssignment(Long id, AssignmentRequestDTO requestDTO) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setTitle(requestDTO.getTitle());
        assignment.setDescription(requestDTO.getDescription());
        assignment.setDueDate(requestDTO.getDueDate());
        assignment.setSubmissionMode(requestDTO.getSubmissionMode());
        assignment.setAttempted(false);

        if (!assignment.getSubject().getId().equals(requestDTO.getSubjectId())) {
            Subject subject = subjectRepository.findById(requestDTO.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            assignment.setSubject(subject);
        }

        if (requestDTO.getCourseId() != null) {
            Course course = courseRepository.findById(requestDTO.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            assignment.setCourse(course);
        }

        assignment.setSem(requestDTO.getSem());

        assignment = assignmentRepository.save(assignment);

        AssiDTO responseDTO = modelMapper.map(assignment, AssiDTO.class);
        responseDTO.setSubjectName(assignment.getSubject().getName());
        responseDTO.setStatus(AssignmentStatusEnum.PENDING);
        return responseDTO;
    }

    @Override
    public void deleteAssignment(Long id) {

        assignmentRepository.deleteById(id);

    }

    @Override
    public List<SubjectAssiDTO> getAllAssignmentsSubjectWise() {

        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream().map(subject -> {
            List<AssiDTO> assignmentDTOs = subject.getAssignment().stream().map(assign -> new AssiDTO(
                    assign.getId(),
                    assign.getTitle(),
                    assign.getDescription(),
                    assign.getDueDate().toString(),
                    assign.getAttempted(),
                    assign.getSubmissionMode(),
                    AssignmentStatusEnum.PENDING, // <-- Always set to PENDING
                    assign.getSubject().getName())).toList();

            return new SubjectAssiDTO(
                    subject.getName(),
                    subject.getCode(),
                    assignmentDTOs);
        }).toList();

    }

    @Override
    public List<AssiDTO> getAssignmentsBySubjectName(String subjectName) {
        List<Assignment> assignments = assignmentRepository.findBySubjectName(subjectName);
        return assignments.stream()
                .map(assignment -> {
                    AssiDTO dto = modelMapper.map(assignment, AssiDTO.class);
                    dto.setSubjectName(assignment.getSubject().getName());
                    dto.setStatus(AssignmentStatusEnum.PENDING); // Always set PENDING

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDTO markAttempted(Long assignmentId, Boolean attempted) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setAttempted(attempted);
        Assignment updated = assignmentRepository.save(assignment);
        return modelMapper.map(updated, AssignmentDTO.class);

    }

    @Override
    public Page<AssiDTO> getAllAssignments(Pageable pageable) {
        Page<Assignment> assignmentPage = assignmentRepository.findAll(pageable);

        return assignmentPage.map(assignment -> {
            AssiDTO dto = modelMapper.map(assignment, AssiDTO.class);
            dto.setSubjectName(assignment.getSubject().getName());
            dto.setStatus(AssignmentStatusEnum.PENDING);
            return dto;
        });
    }

    @Override
    public List<AssiDTO> getOverdueAssignments() {
        LocalDate today = LocalDate.now();
        List<Assignment> overdue = assignmentRepository.findByDueDateBefore(today);

        return overdue.stream()
                .map(assignment -> {
                    AssiDTO dto = modelMapper.map(assignment, AssiDTO.class);
                    dto.setSubjectName(assignment.getSubject().getName());
                    dto.setStatus(AssignmentStatusEnum.OVERDUE); // Set status in response only
                    return dto;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<AssiDTO> getAssignmentsBySubjectAndStudent(Long subjectId, Long studentId) {

        List<Assignment> assignments = assignmentRepository.findAllBySubjectId(subjectId);
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(studentId);

        // Map: assignmentId → attempted (true/false)
        Map<Long, Boolean> attemptedMap = submissions.stream()
                .filter(sub -> sub.getAssignment() != null)
                .collect(Collectors.toMap(
                        sub -> sub.getAssignment().getId(),
                        AssignmentSubmission::isAttempted,
                        (existing, replacement) -> existing // keep first if duplicate
                ));

        return assignments.stream().map(a -> {
            boolean attempted = attemptedMap.getOrDefault(a.getId(), false);

            // Dynamically compute status based on attempted + due date
            AssignmentStatusEnum status;
            if (attempted) {
                status = AssignmentStatusEnum.SUBMITTED;
            } else if (a.getDueDate().isBefore(LocalDate.now())) {
                status = AssignmentStatusEnum.OVERDUE;
            } else {
                status = AssignmentStatusEnum.PENDING;
            }

            return new AssiDTO(
                    a.getId(),
                    a.getTitle(),
                    a.getDescription(),
                    a.getDueDate().toString(),
                    attempted,
                    a.getSubmissionMode(),
                    status,
                    a.getSubject().getName());
        }).collect(Collectors.toList());
    }

    @Override
    public List<AssiDTO> getAssignmentsBySubjectAndAttemptStatus(Long subjectId, Long studentId, boolean attempted) {
        List<Assignment> assignments = assignmentRepository.findAllBySubjectId(subjectId);
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(studentId);

        Map<Long, AssignmentSubmission> submissionMap = submissions.stream()
                .filter(sub -> sub.getAssignment() != null)
                .collect(Collectors.toMap(
                        sub -> sub.getAssignment().getId(),
                        sub -> sub,
                        (existing, replacement) -> existing // in case of duplicates
                ));

        return assignments.stream()
                .filter(a -> submissionMap.containsKey(a.getId()) == attempted) // true if attempted, false if not
                                                                                // attempted
                .map(a -> {
                    AssignmentSubmission sub = submissionMap.get(a.getId());

                    boolean isAttempted = sub != null && sub.isAttempted();
                    AssignmentStatusEnum status;

                    if (sub != null) {
                        status = sub.getStatus(); // from submission
                    } else if (a.getDueDate().isBefore(LocalDate.now())) {
                        status = AssignmentStatusEnum.OVERDUE;
                    } else {
                        status = AssignmentStatusEnum.PENDING;
                    }

                    return new AssiDTO(
                            a.getId(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getDueDate().toString(),
                            isAttempted,
                            a.getSubmissionMode(),
                            status,
                            a.getSubject().getName());
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<AssiDTO> getAllIncompleteAssignmentsByStudent(Long studentId) {
        List<Assignment> allAssignments = assignmentRepository.findAll();
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(studentId);

        Set<Long> attemptedAssignmentIds = submissions.stream()
                .filter(AssignmentSubmission::isAttempted)
                .map(sub -> sub.getAssignment().getId())
                .collect(Collectors.toSet());

        return allAssignments.stream()
                .filter(a -> !attemptedAssignmentIds.contains(a.getId()))
                .map(a -> {
                    AssignmentStatusEnum status = a.getDueDate().isBefore(LocalDate.now())
                            ? AssignmentStatusEnum.OVERDUE
                            : AssignmentStatusEnum.PENDING;

                    return new AssiDTO(
                            a.getId(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getDueDate().toString(),
                            false, // attempted = false
                            a.getSubmissionMode(),
                            status,
                            a.getSubject().getName());
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<AssiDTO> getOverdueAssignmentsByStudent(Long studentId) {
        List<Assignment> allAssignments = assignmentRepository.findAll();
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(studentId);

        // Map: assignmentId → attempted (true/false)
        Set<Long> attemptedAssignmentIds = submissions.stream()
                .filter(AssignmentSubmission::isAttempted)
                .map(sub -> sub.getAssignment().getId())
                .collect(Collectors.toSet());

        return allAssignments.stream()
                .filter(a -> a.getDueDate().isBefore(LocalDate.now()) && // overdue
                        !attemptedAssignmentIds.contains(a.getId()) // not attempted
                )
                .map(a -> new AssiDTO(
                        a.getId(),
                        a.getTitle(),
                        a.getDescription(),
                        a.getDueDate().toString(),
                        false, // attempted = false
                        a.getSubmissionMode(),
                        AssignmentStatusEnum.OVERDUE,
                        a.getSubject().getName()))
                .collect(Collectors.toList());

    }

    @Override
    public AssignmentSummaryDTO getOverallAssignmentSummary() {
        List<Assignment> allAssignments = assignmentRepository.findAll();
        List<AssignmentSubmission> allSubmissions = assignmentSubmissionRepository.findAll();

        int totalAssignments = allAssignments.size();
        int totalSubmitted = (int) allSubmissions.stream().filter(AssignmentSubmission::isAttempted).count();
        int totalNotSubmitted = totalAssignments - totalSubmitted;

        Set<Long> attemptedAssignmentIds = allSubmissions.stream()
                .filter(AssignmentSubmission::isAttempted)
                .map(sub -> sub.getAssignment().getId())
                .collect(Collectors.toSet());

        LocalDate today = LocalDate.now();
        int totalOverdue = (int) allAssignments.stream()
                .filter(a -> a.getDueDate().isBefore(today))
                .filter(a -> !attemptedAssignmentIds.contains(a.getId()))
                .count();

        return new AssignmentSummaryDTO(
                totalAssignments,
                totalSubmitted,
                totalNotSubmitted,
                totalOverdue);

    }

    @Override
    public AssignmentSubmissionStatsDTO getSubmissionStats(Long studentId) {
        StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Subject> subjects = subjectRepository.findByCourseId(student.getCourse().getId());
        List<Long> subjectIds = subjects.stream().map(Subject::getId).toList();

        List<Assignment> assignments = assignmentRepository.findAllBySubjectIdIn(subjectIds);
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(studentId);

        Set<Long> submittedIds = submissions.stream()
                .filter(AssignmentSubmission::isAttempted)
                .map(sub -> sub.getAssignment().getId())
                .collect(Collectors.toSet());

        int totalAssignments = assignments.size();
        int totalSubmitted = submittedIds.size();

        double submissionPercentage = totalAssignments == 0 ? 0.0
                : (totalSubmitted * 100.0) / totalAssignments;

        return new AssignmentSubmissionStatsDTO(
                totalAssignments,
                totalSubmitted,
                Math.round(submissionPercentage * 100.0) / 100.0);
    }

    @Override
    public List<AssiDTO> getAssignmentsByDueDate(LocalDate dueDate) {
        return assignmentRepository.findByDueDate(dueDate)
                .stream()
                .map(assignment -> new AssiDTO(
                        assignment.getId(),
                        assignment.getTitle(),
                        assignment.getDescription(),
                        assignment.getDueDate().toString(),
                        assignment.getAttempted(),
                        assignment.getSubmissionMode(),
                        AssignmentStatusEnum.PENDING, // Always return PENDING
                        assignment.getSubject().getName()))
                .collect(Collectors.toList());

    }

    @Override
    public AssiDTO createAssignment(AssignmentRequestDTO requestDTO) {

        Subject subject = subjectRepository.findById(requestDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Course course = courseRepository.findById(requestDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Assignment assignment = new Assignment();
        assignment.setTitle(requestDTO.getTitle());
        assignment.setDescription(requestDTO.getDescription());
        assignment.setDueDate(requestDTO.getDueDate());
        assignment.setSubmissionMode(requestDTO.getSubmissionMode());
        assignment.setSubject(subject);
        assignment.setCourse(course);
        assignment.setSem(requestDTO.getSem());
        assignment.setAttempted(false);
        assignment = assignmentRepository.save(assignment);

        AssiDTO responseDTO = modelMapper.map(assignment, AssiDTO.class);
        responseDTO.setSubjectName(subject.getName());
        responseDTO.setStatus(AssignmentStatusEnum.PENDING);

        return responseDTO;
    }

    @Override
    public List<AssiDTO> getAllCompletedAssignmentsByStudent(Long studentId) {
        List<AssignmentSubmission> submissions = assignmentSubmissionRepository.findByStudentId(studentId);

        return submissions.stream()
                .filter(sub -> sub.isAttempted() && sub.getStatus() == AssignmentStatusEnum.SUBMITTED)
                .map(sub -> {
                    Assignment a = sub.getAssignment();
                    return new AssiDTO(
                            a.getId(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getDueDate().toString(),
                            true,
                            a.getSubmissionMode(),
                            sub.getStatus(),
                            a.getSubject().getName());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<AssiDTO> getByCourseIdAndSem(Long courseId, Integer sem, Pageable pageable) {
        Page<Assignment> assignments = assignmentRepository.findByCourseIdAndSem(courseId, sem, pageable);

        return assignments.map(assignment -> {
            AssiDTO dto = modelMapper.map(assignment, AssiDTO.class);
            dto.setSubjectName(assignment.getSubject().getName());
            return dto;
        });

    }

}
