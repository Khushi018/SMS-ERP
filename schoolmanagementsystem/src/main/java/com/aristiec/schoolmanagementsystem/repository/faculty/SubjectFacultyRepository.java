package com.aristiec.schoolmanagementsystem.repository.faculty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.faculty.Faculty;
import com.aristiec.schoolmanagementsystem.modal.faculty.SubjectFaculty;

@Repository
public interface SubjectFacultyRepository extends JpaRepository<SubjectFaculty,Long> {
         List<SubjectFaculty> findBySubjectId(Long subjectId);
          List<SubjectFaculty> findByFaculty(Faculty faculty);

    
} 