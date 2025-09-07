package com.aristiec.schoolmanagementsystem.transport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aristiec.schoolmanagementsystem.transport.modal.StoppageGroup;

public interface StoppageGroupRepository extends JpaRepository<StoppageGroup, Long> {
}