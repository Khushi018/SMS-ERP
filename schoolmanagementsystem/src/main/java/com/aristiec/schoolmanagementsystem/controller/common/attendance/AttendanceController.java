package com.aristiec.schoolmanagementsystem.controller.common.attendance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.AttendDTO;
import com.aristiec.schoolmanagementsystem.dto.OverallAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.attendance.AttendanceResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.attendance.SubjectwiseAttendanceDTO;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;
import com.aristiec.schoolmanagementsystem.service.attendance.AttendanceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/attendance")

@SecurityRequirement(name = "bearerAuth") 
public class AttendanceController {
     
    @Autowired
    private AttendanceService attendanceService;

@PostMapping("/mark")
public ResponseEntity<String> markAttendance(@RequestBody List<AttendDTO> requestList) {
    attendanceService.markAttendance(requestList);
    return ResponseEntity.ok("Attendance marked for all students successfully.");
}

  @GetMapping("/{id}")
public ResponseEntity<AttendanceResponseDTO> getAttendanceById(@PathVariable Long id) {
    return ResponseEntity.ok(attendanceService.getAttendanceById(id));
}

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
        boolean deleted = attendanceService.deleteAttendance(id);
        if (deleted) {
            return ResponseEntity.ok("Attendance deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Attendance not found with id: " + id);
        }
    }

  @PutMapping("/{id}")
public ResponseEntity<String> updateAttendance( @PathVariable Long id, @RequestBody AttendDTO dto) {
    return ResponseEntity.ok(attendanceService.updateAttendance(id, dto));
}




    @GetMapping("/overall")
public ResponseEntity<OverallAttendanceDTO> getOverallAttendance(@RequestParam Long studentId) {
    return ResponseEntity.ok(attendanceService.getOverallAttendance(studentId));
}

@GetMapping("/subjectwise")
public ResponseEntity<List<SubjectwiseAttendanceDTO>> getSubjectWiseAttendance(
        @RequestParam Long studentId) {
    return ResponseEntity.ok(attendanceService.getSubjectWiseAttendance(studentId));
}


 @GetMapping("/monthly-overall")
public ResponseEntity<OverallAttendanceDTO> getMonthlyOverallAttendance(
        @RequestParam Long studentId,
        @RequestParam int month,
        @RequestParam int year) {
    return ResponseEntity.ok(attendanceService.getMonthlyOverallAttendance(studentId, month, year));
}


}
