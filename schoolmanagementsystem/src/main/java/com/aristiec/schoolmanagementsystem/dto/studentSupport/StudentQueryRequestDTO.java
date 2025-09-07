package com.aristiec.schoolmanagementsystem.dto.studentSupport;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QuerySendTo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class StudentQueryRequestDTO {
    private QuerySendTo sendTo;
    private QueryCategory category;
    private String subject;
    private String description;
    private MultipartFile file;
    
}
