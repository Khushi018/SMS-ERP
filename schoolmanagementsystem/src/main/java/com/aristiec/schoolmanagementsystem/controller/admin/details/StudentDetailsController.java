package com.aristiec.schoolmanagementsystem.controller.admin.details;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.dto.ParentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.SemesterPerformanceDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentCourseSubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.StudentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.StudentDetailsResponseDto;
import com.aristiec.schoolmanagementsystem.dto.StudentListResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aristiec.schoolmanagementsystem.service.details.StudentDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@Tag(name = "Student Admission", description = "Manage student admissions and their related details")

@SecurityRequirement(name = "bearerAuth")
public class StudentDetailsController {

    @Autowired
    private StudentDetailsService studentDetailsService;

    @Operation(summary = "Delete a student", description = "Deletes a student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        studentDetailsService.deleteStudentById(studentId);
        return ResponseEntity.ok("Student deleted successfully!");
    }

    @Operation(summary = "Get all students By Page or Size, Or Null Gives All students", description = "Retrieves a list of all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of students fetched successfully"),
            @ApiResponse(responseCode = "204", description = "No students available", content = @Content)
    })

    // GET with pagination & sorting
    @GetMapping("/all/paginated")
    public ResponseEntity<Page<StudentDetailsResponseDto>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDetailsResponseDto> result = studentDetailsService.getAllStudents(pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/admission", consumes = "application/json")
    @Operation(summary = "Admit a student", description = "Admits a student by saving their admission and profile details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student admitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid student data", content = @Content)
    })
    public ResponseEntity<StudentDetailsResponseDto> admitStudent(@RequestBody StudentDetailsDto dto) {
        StudentDetailsResponseDto response = studentDetailsService.admitStudent(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDetailsResponseDto> getStudentDetails(@PathVariable Long studentId) {
        StudentDetailsResponseDto studentDetailsDto = studentDetailsService.getStudentDetailsById(studentId);
        return ResponseEntity.ok(studentDetailsDto);
    }

    @GetMapping("/{studentId}/parent")
    @Operation(summary = "Get parent details", description = "Retrieves the parent details associated with the given student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent details fetched"),
            @ApiResponse(responseCode = "404", description = "Parent details not found", content = @Content)
    })
    public ResponseEntity<ParentDetailsDto> getParentDetails(
            @Parameter(description = "ID of the student") @PathVariable Long studentId) {
        ParentDetailsDto parentDetailsDto = studentDetailsService.getParentDetailsByStudentId(studentId);
        return ResponseEntity.ok(parentDetailsDto);
    }

    @GetMapping("/code/{studentCode}")
    public ResponseEntity<StudentDetailsResponseDto> getStudentByCode(@PathVariable String studentCode) {
        StudentDetailsResponseDto studentDto = studentDetailsService.getStudentByCode(studentCode);
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentDetailsResponseDto> getStudentByEmail(@PathVariable String email) {
        StudentDetailsResponseDto studentDto = studentDetailsService.getStudentByEmail(email);
        if (studentDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studentDto);
    }


    @GetMapping("/all")
     @Operation(summary = "Get all students By all dynamic filters, Or Null Gives All students", description = "Retrieves a list of all students")
    public ResponseEntity<StudentListResponseDto> getStudents(
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String section,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String email, @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) String aadharNumber, @RequestParam(required = false) String gender,
            @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        StudentListResponseDto students = studentDetailsService.getAllwithFilters(courseCode,
                year, section, level, email, mobileNumber, aadharNumber, gender, firstName, lastName);

        if (students == null || students.getStudents() == null || students.getStudents().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}/course-subjects")
    public ResponseEntity<StudentCourseSubjectMinimalDTO> getStudentCourseAndSubjectsMinimal(
            @PathVariable Long studentId) {
        StudentCourseSubjectMinimalDTO dto = studentDetailsService.getStudentCourseAndSubjectNames(studentId);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/admission/{studentId}")
    @Operation(summary = "Update admitted student", description = "Updates the details of an admitted student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid student data", content = @Content)
    })
    public ResponseEntity<StudentDetailsResponseDto> updateAdmittedStudent(
        @PathVariable Long studentId,
        @RequestBody StudentDetailsDto dto) {
        StudentDetailsResponseDto updatedStudent = studentDetailsService.updateAdmittedStudent(studentId, dto);
        if (updatedStudent == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }


}
