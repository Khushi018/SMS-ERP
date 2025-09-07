package com.aristiec.schoolmanagementsystem.repository.subject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;


@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
          List<Subject> findByCourseId(Long courseId);

} 