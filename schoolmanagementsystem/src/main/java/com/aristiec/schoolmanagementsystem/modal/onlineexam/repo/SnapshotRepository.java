package com.aristiec.schoolmanagementsystem.modal.onlineexam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.WebcamSnapshot;

public interface SnapshotRepository extends JpaRepository<WebcamSnapshot, Long> {
}
