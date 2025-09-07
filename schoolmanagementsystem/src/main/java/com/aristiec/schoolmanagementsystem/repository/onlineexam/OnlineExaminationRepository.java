package com.aristiec.schoolmanagementsystem.repository.onlineexam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.OnlineExamination;

public interface OnlineExaminationRepository extends JpaRepository<OnlineExamination, Long> {
    List<OnlineExamination> findByCourseIdAndSemester(Long courseId, Integer semester);

}
