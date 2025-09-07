package com.aristiec.schoolmanagementsystem.controller.faculty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.dto.faculty.AssignedSubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultyDTO;
import com.aristiec.schoolmanagementsystem.service.faculty.FacultyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/faculty")
@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Faculty Management", description = "Manage faculty members and their related details")
public class FacultyController {
    
    @Autowired
    private FacultyService facultyService;

    @PostMapping
    public FacultyDTO addFaculty(@RequestBody FacultyDTO dto) {
        return facultyService.addFaculty(dto);
    }

    @GetMapping("/{id}")
    public FacultyDTO getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @GetMapping
    public List<FacultyDTO> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @PutMapping("/{id}")
    public FacultyDTO updateFaculty(@PathVariable Long id, @RequestBody FacultyDTO dto) {
        return facultyService.updateFaculty(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/department/{departmentId}")
public List<FacultyDTO> getByDepartment(@PathVariable Long departmentId) {
    return facultyService.getFacultyByDepartment(departmentId);
}

@GetMapping("/hods")
public List<FacultyDTO> getAllHODs() {
    return facultyService.getAllHODs();
}

@GetMapping("/designation/{designation}")
public List<FacultyDTO> getFacultyByPosition(@PathVariable FacultyDesignation designation) {
    return facultyService.getFacultyByPosition(designation);
}


@GetMapping("/assigned-subjects")
public ResponseEntity<List<AssignedSubjectDTO>> getAssignedSubjects(@RequestParam Long facultyId) {
    return ResponseEntity.ok(facultyService.getAssignedSubjectsWithSectionByFaculty(facultyId));
}



@Operation(
    summary = "Get Faculty by Email",
    description = "Returns basic information of a faculty including full name, faculty ID, email, phone number, position, etc."
)
@GetMapping("/basic-info")
public ResponseEntity<FacultyDTO> getBasicFacultyInfo(@RequestParam String email) {
    return ResponseEntity.ok(facultyService.getFacultyByEmail(email));
}



}
