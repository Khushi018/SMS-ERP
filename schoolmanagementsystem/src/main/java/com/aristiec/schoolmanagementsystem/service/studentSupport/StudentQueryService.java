package com.aristiec.schoolmanagementsystem.service.studentSupport;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.ReplyRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryFilterResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryResponseDTO;

public interface StudentQueryService {
         StudentQueryResponseDTO submitQuery(Long studentId, StudentQueryRequestDTO requestDTO);
    List<StudentQueryResponseDTO> getQueriesByStudent(Long studentId);
     List<StudentQueryResponseDTO> getAllQueries();
     List<StudentQueryResponseDTO> getQueriesByStatus(QueryStatus status);
     StudentQueryResponseDTO getQueryById(Long id);
   StudentQueryResponseDTO  replyToQuery(Long queryId, ReplyRequestDTO dto);
   List<StudentQueryFilterResponseDTO> getStudentQueriesFiltered(Long studentId, QueryCategory category, QueryStatus status);
   List<StudentQueryFilterResponseDTO> searchStudentQueries(Long studentId, String subject, QueryCategory category, QueryStatus status, LocalDate submittedAt);
   List<StudentQueryFilterResponseDTO> getAllQueriesByStudentId(Long studentId);


    void deleteQuery(Long queryId);
    byte[] getQueryFile(Long queryId);
} 
