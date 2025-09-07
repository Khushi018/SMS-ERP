package com.aristiec.schoolmanagementsystem.repository.studentSupport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;
import com.aristiec.schoolmanagementsystem.modal.studentSupport.StudentQuery;

@Repository
public interface StudentQueryRepository extends JpaRepository<StudentQuery,Long> {
    List<StudentQuery> findByStudentId(Long studentId);
List<StudentQuery> findByStatus(QueryStatus status);
List<StudentQuery> findByStudentIdAndCategoryAndStatus(Long studentId, QueryCategory category, QueryStatus status);
List<StudentQuery> findByStudentIdAndCategory(Long studentId, QueryCategory category);
List<StudentQuery> findByStudentIdAndStatus(Long studentId, QueryStatus status);

}
