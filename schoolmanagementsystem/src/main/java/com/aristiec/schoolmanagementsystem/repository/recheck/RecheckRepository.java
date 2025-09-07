package com.aristiec.schoolmanagementsystem.repository.recheck;

import com.aristiec.schoolmanagementsystem.constant.enums.RecheckEnum;
import com.aristiec.schoolmanagementsystem.modal.recheck.Recheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecheckRepository extends JpaRepository<Recheck, Long> {
    //student ID
    @Query("SELECT r FROM Recheck r WHERE r.student.id = :studentId")
    List<Recheck> findByStudentId(Long studentId);

    //status
    @Query("SELECT r FROM Recheck r WHERE r.status = :status")
    List<Recheck> findByStatus(RecheckEnum status);

    //exam
    @Query("SELECT r FROM Recheck r WHERE r.exam.id = :examId")
    List<Recheck> findByExamId(Long examId);

    //summary
    @Query("SELECT COUNT(r) FROM Recheck r WHERE r.student.id = :studentId AND r.status = :status")
    long countByStudentIdAndStatus(Long studentId, RecheckEnum status);

    @Modifying
    @Transactional
    @Query("DELETE FROM Recheck r WHERE r.student.id = :studentId")
    void deleteByStudentId(Long studentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Recheck r WHERE r.exam.id = :examId")
    void deleteByExamId(Long examId);
}
