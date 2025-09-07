package com.aristiec.schoolmanagementsystem.repository.announcement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.announcement.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
    
    
} 
