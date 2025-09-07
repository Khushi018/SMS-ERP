package com.aristiec.schoolmanagementsystem.serviceImpl.subjectFeedback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.subjectFeedback.FeedbackResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectFeedback.FeedbackrequestDTO;
import com.aristiec.schoolmanagementsystem.modal.subjectFeedback.Feedback;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.repository.subjectFeedback.FeedbackRepository;
import com.aristiec.schoolmanagementsystem.service.subjectFeedback.FeedbackService;

@Service
public class FeedbackServiceImpl  implements FeedbackService{

      @Autowired
    private FeedbackRepository feedbackRepository;
     @Autowired
    private StudentDetailsRepository studentDetailsRepository;
     @Autowired
    private SubjectRepository subjectRepository;
     @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedbackResponseDTO submitFeedback(FeedbackrequestDTO feedbackDTO) {
        Feedback feedback=new Feedback();
          
        feedback.setStudentDetails(studentDetailsRepository.findById(feedbackDTO.getStudentId()).orElseThrow());
        feedback.setSubject(subjectRepository.findById(feedbackDTO.getSubjectId()).orElseThrow());
        feedback.setFeedbackText(feedbackDTO.getFeedbackText());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setSubmittedAt(LocalDateTime.now());

        Feedback saved=feedbackRepository.save(feedback);
        return convertToResponseDTO(saved);
    }

    @Override
    public List<FeedbackResponseDTO> getFeedbackBySubject(Long subjectId) {
         return feedbackRepository.findBySubjectId(subjectId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
       }

    @Override
    public List<FeedbackResponseDTO> getFeedbackByStudent(Long studentId) {
       return feedbackRepository.findByStudentDetailsId(studentId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    private FeedbackResponseDTO convertToResponseDTO(Feedback feedback) {
        FeedbackResponseDTO dto = modelMapper.map(feedback, FeedbackResponseDTO.class);
        dto.setStudentCode(feedback.getStudentDetails().getStudentCode());
        dto.setSubjectName(feedback.getSubject().getName());
        return dto;
    }
    
}
