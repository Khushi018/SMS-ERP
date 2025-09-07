package com.aristiec.schoolmanagementsystem.serviceImpl.onlineexam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.aristiec.schoolmanagementsystem.constant.enums.QuestionType;
import com.aristiec.schoolmanagementsystem.dto.onlineexam.*;
import com.aristiec.schoolmanagementsystem.exception.EntityNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.onlineexam.*;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.onlineexam.ExamContentRepository;
import com.aristiec.schoolmanagementsystem.repository.onlineexam.ExamSubmissionRepository;
import com.aristiec.schoolmanagementsystem.repository.onlineexam.OnlineExaminationRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.service.onlineexam.OnlineExamService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlineExamServiceImpl implements OnlineExamService {

    private final OnlineExaminationRepository examRepo;
    private final ExamContentRepository contentRepo;
    private final ExamSubmissionRepository submissionRepo;
    private final CourseRepository courseRepo;
    private final SubjectRepository subjectRepo;
    private final StudentDetailsRepository studentRepo;
    private final ModelMapper mapper;

    @Override
    public OnlineExaminationDto createExam(OnlineExaminationDto dto) {
        OnlineExamination exam = mapper.map(dto, OnlineExamination.class);

        exam.setSemester(dto.getSemester()); // ✅ manually mapped here

        exam.setCourse(courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found")));

        exam.setSubject(subjectRepo.findById(dto.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject not found")));

        exam.setStartTime(dto.getStartTime());
        exam.setExamDate(dto.getExamDate());

        List<ExamContent> contents = new ArrayList<>();
        for (ExamContentDto q : dto.getContents()) {
            ExamContent content = mapper.map(q, ExamContent.class);
            content.setExam(exam);
            contents.add(content);
        }

        exam.setContents(contents);

        OnlineExamination saved = examRepo.save(exam);
        return mapper.map(saved, OnlineExaminationDto.class); // Optionally map back
    }

    @Override
    public List<OnlineExaminationDto> getExamsByCourseAndSemester(Long courseId, Integer semester) {
        List<OnlineExamination> exams = examRepo.findByCourseIdAndSemester(courseId, semester);
        return exams.stream()
                .map(exam -> mapper.map(exam, OnlineExaminationDto.class))
                .toList();
    }

    @Override
    public List<ExamContentDto> getOnlyContentsByExamId(Long examId) {
        OnlineExamination exam = examRepo.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id " + examId));

        return exam.getContents().stream().map(content -> {
            ExamContentDto dto = new ExamContentDto();
            dto.setId(content.getId());
            dto.setQuestion(content.getQuestion());
            dto.setMarks(content.getMarks());
            dto.setQuestionType(content.getQuestionType().name()); // ✅ fix here
            dto.setOptions(content.getOptions());
            dto.setCorrectIndex(null); // optionally hide this for public view
            return dto;
        }).toList();
    }

    @Override
    public List<ExamContentDto> getContentsWithAnswersByExamId(Long examId) {
        OnlineExamination exam = examRepo.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id " + examId));

        return exam.getContents().stream().map(content -> {
            ExamContentDto dto = new ExamContentDto();
            dto.setId(content.getId());
            dto.setQuestion(content.getQuestion());
            dto.setMarks(content.getMarks());
            dto.setQuestionType(content.getQuestionType().name());
            dto.setOptions(content.getOptions());
            dto.setCorrectIndex(content.getCorrectIndex()); // ✅ include answer
            return dto;
        }).toList();
    }

    @Override
    public ExamContentDto addContent(Long examId, ExamContentDto dto) {
        OnlineExamination exam = examRepo.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));
        ExamContent content = mapper.map(dto, ExamContent.class);
        content.setExam(exam);
        content = contentRepo.save(content);
        return mapper.map(content, ExamContentDto.class);
    }

    @Override
    public List<OnlineExaminationDto> getAllExams(Long courseId, Long subjectId,
            LocalDate startDate, LocalDate endDate) {
        return examRepo.findAll().stream()
                .filter(e -> (courseId == null || e.getCourse().getId().equals(courseId)) &&
                        (subjectId == null || e.getSubject().getId().equals(subjectId)) &&
                        (startDate == null || !e.getStartTime().toLocalDate().isBefore(startDate)) &&
                        (endDate == null || !e.getStartTime().toLocalDate().isAfter(endDate)))
                .map(e -> mapper.map(e, OnlineExaminationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OnlineExaminationStudentDto> getAllExamsForStudent(Long studentId) {
        List<ExamSubmission> submissions = submissionRepo.findByStudentId(studentId);
        List<Long> submittedExamIds = submissions.stream()
                .map(s -> s.getExam().getId())
                .collect(Collectors.toList());

        return examRepo.findAll().stream()
                .map(e -> mapper.map(e, OnlineExaminationStudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OnlineExaminationDto getExamById(Long id) {
        OnlineExamination exam = examRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));
        return mapper.map(exam, OnlineExaminationDto.class);
    }

    @Override
    @Transactional
    public ExamSubmissionDto submitExam(Long examId, SubmitExamDto dto) {
        OnlineExamination exam = examRepo.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id " + examId));

        StudentDetails student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + dto.getStudentId()));

        ExamSubmission submission = new ExamSubmission();
        submission.setExam(exam);
        submission.setStudent(student);
        submission.setSubmittedAt(LocalDateTime.now());

        List<Answer> answerList = new ArrayList<>();
        int autoScore = 0;

        for (AnswerDto answerDto : dto.getAnswers()) {
            ExamContent question = contentRepo.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Question not found with id " + answerDto.getQuestionId()));

            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setSubmission(submission);

            if (question.getQuestionType() == QuestionType.MCQ) {
                answer.setSelectedOption(answerDto.getAnswer());
                boolean correct = question.getOptions().contains(answerDto.getAnswer()) &&
                        question.getOptions().indexOf(answerDto.getAnswer()) == question.getCorrectIndex();
                answer.setCorrect(correct);
                answer.setMarksAwarded(correct ? question.getMarks() : 0);
                autoScore += answer.getMarksAwarded();
            } else {
                answer.setDescriptiveAnswer(answerDto.getAnswer());
                answer.setPdfUrl(answerDto.getPdfUrl());
                answer.setMarksAwarded(null);
            }

            answerList.add(answer);
        }

        submission.setAnswers(answerList);
        submission.setScore(autoScore);

        ExamSubmission saved = submissionRepo.save(submission);
        return mapper.map(saved, ExamSubmissionDto.class);
    }

    @Override
    public ExamSubmissionDto getSubmission(Long submissionId) {
        ExamSubmission submission = submissionRepo.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Submission not found"));
        return mapper.map(submission, ExamSubmissionDto.class);
    }

    @Override
    public List<ExamSubmissionDto> listSubmissions(Long examId, Long studentId) {
        List<ExamSubmission> submissions;
        if (examId != null) {
            submissions = submissionRepo.findByExamId(examId);
        } else if (studentId != null) {
            submissions = submissionRepo.findByStudentId(studentId);
        } else {
            submissions = submissionRepo.findAll();
        }

        return submissions.stream()
                .map(sub -> mapper.map(sub, ExamSubmissionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExamSubmissionDto gradeSubmission(Long examId, Long submissionId, GradeDto grade) {
        // Check if exam exists
        if (!examRepo.existsById(examId)) {
            throw new EntityNotFoundException("Exam not found with id " + examId);
        }

        // Check if submission exists
        boolean submissionExists = submissionRepo.existsById(submissionId);
        if (!submissionExists) {
            throw new EntityNotFoundException("Submission not found with id " + submissionId);
        }

        // Check if submission belongs to the exam
        ExamSubmission sub = submissionRepo.findByIdAndExamId(submissionId, examId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Submission with id " + submissionId + " not found for exam id " + examId));

        sub.setScore(grade.getScore());
        return mapper.map(submissionRepo.save(sub), ExamSubmissionDto.class);
    }

    @Override
    public void deleteAllExams() {
        submissionRepo.deleteAll(); // Delete all exam submissions first
        examRepo.deleteAll();
    }
}
