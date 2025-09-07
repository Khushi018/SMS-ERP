package com.aristiec.schoolmanagementsystem.repository.department;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.staff.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
        Optional<Department> findByName(String name);
    
} 