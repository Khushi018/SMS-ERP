package com.aristiec.schoolmanagementsystem.repository.subjectFeedback;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.subjectFeedback.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback ,Long> {
      List<Feedback> findBySubjectId(Long subjectId);
  List<Feedback> findByStudentDetailsId(Long studentId);

}
