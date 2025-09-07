package com.aristiec.schoolmanagementsystem.controller.announcement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.announcement.AnnouncementRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.announcement.AnnouncementResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.announcement.StudentAnnouncementResponseDTO;
import com.aristiec.schoolmanagementsystem.service.announcement.AnnouncementService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/v1/announcements")
@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Announcement Management", description = "Manage announcements for students and faculty")   
public class AnnouncementController {
     
    @Autowired
    private AnnouncementService announcementService;
          

    
    @PostMapping
    public ResponseEntity<AnnouncementResponseDTO> create(@RequestBody AnnouncementRequestDTO dto) {
        return ResponseEntity.ok(announcementService.createAnnouncement(dto));
    }


      @GetMapping
    public ResponseEntity<List<AnnouncementResponseDTO>> getAll() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

       @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    }

     @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDTO> update(@PathVariable Long id, @RequestBody AnnouncementRequestDTO dto) {
        return ResponseEntity.ok(announcementService.updateAnnouncement(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok("Announcement deleted successfully.");
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentAnnouncementResponseDTO>> getForStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(announcementService.getAnnouncementsForStudent(studentId));
    }

}