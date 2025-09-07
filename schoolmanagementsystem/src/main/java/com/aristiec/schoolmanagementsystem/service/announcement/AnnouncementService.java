package com.aristiec.schoolmanagementsystem.service.announcement;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.announcement.AnnouncementRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.announcement.AnnouncementResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.announcement.StudentAnnouncementResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.announcement.Announcement;

public interface AnnouncementService {

    
    AnnouncementResponseDTO createAnnouncement(AnnouncementRequestDTO dto);
    List<AnnouncementResponseDTO> getAllAnnouncements();
    AnnouncementResponseDTO getAnnouncementById(Long id);

    AnnouncementResponseDTO updateAnnouncement(Long id, AnnouncementRequestDTO dto);

    void deleteAnnouncement(Long id);
    
List<StudentAnnouncementResponseDTO> getAnnouncementsForStudent(Long studentId);

    
} 