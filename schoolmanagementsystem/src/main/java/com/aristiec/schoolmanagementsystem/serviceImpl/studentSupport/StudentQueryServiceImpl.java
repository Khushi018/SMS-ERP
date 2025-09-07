package com.aristiec.schoolmanagementsystem.serviceImpl.studentSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.ReplyRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryFilterResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.studentSupport.StudentQuery;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.studentSupport.StudentQueryRepository;
import com.aristiec.schoolmanagementsystem.service.studentSupport.StudentQueryService;

@Service
public class StudentQueryServiceImpl implements StudentQueryService {
    
    @Autowired
    private StudentQueryRepository studentQueryRepository;
     
    @Autowired
    private StudentDetailsRepository studentDetailsRepository;
     
    @Autowired
    private ModelMapper modelMapper;

   

    @Override
    public List<StudentQueryResponseDTO> getQueriesByStudent(Long studentId) {
         List<StudentQuery> queries = studentQueryRepository.findByStudentId(studentId);
        return queries.stream().map(this::mapToResponse).collect(Collectors.toList());

       
        
    }

     @Override
        public void deleteQuery(Long id) {
            StudentQuery query = studentQueryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Query not found"));
            studentQueryRepository.delete(query);
        }
        
    @Override
    public List<StudentQueryResponseDTO> getAllQueries() {
        List<StudentQuery> queries = studentQueryRepository.findAll();
        return queries.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

   

    @Override
    public StudentQueryResponseDTO getQueryById(Long id) {
         StudentQuery query = studentQueryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Query not found"));
        return mapToResponse(query);
    }

    @Override
    public StudentQueryResponseDTO replyToQuery(Long queryId, ReplyRequestDTO dto) {
         StudentQuery query = studentQueryRepository.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));

        query.setStatus(dto.getStatus());
        query.setRepliedAt(LocalDateTime.now());

        return mapToResponse(studentQueryRepository.save(query));

      }


   

   
    @Override
    public byte[] getQueryFile(Long queryId) {
        StudentQuery query = studentQueryRepository.findById(queryId)
        .orElseThrow(() -> new RuntimeException("Query not found"));

    if (query.getFileData() == null) {
        throw new RuntimeException("No file attached to this query");
    }

    return query.getFileData();
    }

    @Override
    public StudentQueryResponseDTO submitQuery(Long studentId, StudentQueryRequestDTO dto) {
          StudentDetails student = studentDetailsRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentQuery query = new StudentQuery();
        query.setSendTo(dto.getSendTo());
        query.setCategory(dto.getCategory());
        query.setSubject(dto.getSubject());
        query.setDescription(dto.getDescription());
        query.setStatus(QueryStatus.OPEN);
        query.setSubmittedAt(LocalDateTime.now());
        query.setStudent(student);

        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            try {
                query.setFileData(dto.getFile().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error processing file", e);
            }
        }

        return mapToResponse(studentQueryRepository.save(query));
    }
    


    private StudentQueryResponseDTO mapToResponse(StudentQuery query) {
       StudentDetails student = query.getStudent();
    return new StudentQueryResponseDTO(
        query.getId(),
        query.getSendTo(),
        query.getCategory(),
        query.getSubject(),
        query.getDescription(),
        query.getStatus(),
        query.getSubmittedAt(),
        query.getRepliedAt(),
        student.getId(),
        student.getFirstName() + " " + student.getLastName()
    );
    }

    @Override
    public List<StudentQueryResponseDTO> getQueriesByStatus(QueryStatus status) {
        List<StudentQuery> queries = studentQueryRepository.findByStatus(status);
    return queries.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<StudentQueryFilterResponseDTO> getStudentQueriesFiltered(Long studentId, QueryCategory category,
            QueryStatus status) {

                 List<StudentQuery> queries;

    if (category != null && status != null) {
        queries = studentQueryRepository.findByStudentIdAndCategoryAndStatus(studentId, category, status);
    } else if (category != null) {
        queries = studentQueryRepository.findByStudentIdAndCategory(studentId, category);
    } else if (status != null) {
        queries = studentQueryRepository.findByStudentIdAndStatus(studentId, status);
    } else {
        queries = studentQueryRepository.findByStudentId(studentId);
    }

    return queries.stream()
            .map(query -> new StudentQueryFilterResponseDTO(
                    query.getSubject(),
                    query.getCategory(),
                    query.getStatus(),
                    query.getSubmittedAt()
            ))
            .toList();

}

    @Override
    public List<StudentQueryFilterResponseDTO> searchStudentQueries(Long studentId, String subject,
            QueryCategory category, QueryStatus status, LocalDate submittedAt) {
               List<StudentQuery> queries = studentQueryRepository.findByStudentId(studentId);

    return queries.stream()
        .filter(q -> subject == null || q.getSubject().toLowerCase().contains(subject.toLowerCase()))
        .filter(q -> category == null || q.getCategory() == category)
        .filter(q -> status == null || q.getStatus() == status)
        .filter(q -> submittedAt == null || q.getSubmittedAt().toLocalDate().equals(submittedAt))
        .map(q -> new StudentQueryFilterResponseDTO(
            q.getSubject(),
            q.getCategory(),
            q.getStatus(),
            q.getSubmittedAt()
        ))
        .toList();

            }

    @Override
    public List<StudentQueryFilterResponseDTO> getAllQueriesByStudentId(Long studentId) {
         return studentQueryRepository.findByStudentId(studentId)
        .stream()
        .map(q -> new StudentQueryFilterResponseDTO(
            q.getSubject(),
            q.getCategory(),
            q.getStatus(),
            q.getSubmittedAt()
        ))
        .toList();
    }
   
    
}
