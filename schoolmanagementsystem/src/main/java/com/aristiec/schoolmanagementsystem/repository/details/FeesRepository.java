package com.aristiec.schoolmanagementsystem.repository.details;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.admission.Fee;

@Repository
public interface FeesRepository extends JpaRepository<Fee,Long> {
 List<Fee> findByStudentDetailsId(Long studentId);
 @Query("SELECT SUM(f.amount) FROM Fee f")
  BigDecimal getTotalFeeAmount();

 


    
} 