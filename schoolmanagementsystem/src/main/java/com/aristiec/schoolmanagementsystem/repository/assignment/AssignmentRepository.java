package com.aristiec.schoolmanagementsystem.repository.assignment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.assignment.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long>{
        List<Assignment> findBySubjectName(String subjectName);
       List<Assignment> findByDueDateBefore(LocalDate today);
        @Query("SELECT a FROM Assignment a WHERE a.subject.id = :subjectId")
List<Assignment> findAllBySubjectId(@Param("subjectId") Long subjectId);

     List<Assignment> findByDueDate(LocalDate dueDate);

     List<Assignment> findAllBySubjectIdIn(List<Long> subjectIds);

 Page<Assignment> findByCourseIdAndSem(Long courseId, Integer sem, Pageable pageable);



} 
