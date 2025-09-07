package com.aristiec.schoolmanagementsystem.controller.faculty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.faculty.SubjectFacultyDTO;
import com.aristiec.schoolmanagementsystem.service.faculty.SubjectFacultyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/subject-faculty")

@SecurityRequirement(name = "bearerAuth") 
public class SubjectFacultyController {

     @Autowired
    private SubjectFacultyService facultyService;

    @PostMapping("/assign")
    public ResponseEntity<SubjectFacultyDTO> createFaculty(@RequestBody SubjectFacultyDTO facultyDTO) {
        return new ResponseEntity<>(facultyService.assignFacultyToSubject(facultyDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubjectFacultyDTO>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllAssignedFaculties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectFacultyDTO> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getAssignedFacultyById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.removeAssignedFaculty(id);;
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectFacultyDTO> updateFaculty(@PathVariable Long id, @RequestBody SubjectFacultyDTO facultyDTO) {
        SubjectFacultyDTO updatedFaculty = facultyService.updateAssignedFaculty(id, facultyDTO);
        return ResponseEntity.ok(updatedFaculty);
    }
    
}
