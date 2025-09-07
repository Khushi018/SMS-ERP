package com.aristiec.schoolmanagementsystem.serviceImpl;

import com.aristiec.schoolmanagementsystem.constant.enums.StudentStatus;
import com.aristiec.schoolmanagementsystem.modal.persist.StudentRegistration;
import com.aristiec.schoolmanagementsystem.repository.StudentRegRepository;
import com.aristiec.schoolmanagementsystem.service.StudentRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentRegServiceImpl  implements StudentRegService {
    
    @Autowired
    private StudentRegRepository studentRegRepository;

    @Override
    public StudentRegistration registerStudent(StudentRegistration studentRegistration) {
        studentRegistration.setStatus(StudentStatus.PENDING);
        return studentRegRepository.save(studentRegistration);
    }
}
