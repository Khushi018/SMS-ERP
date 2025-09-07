package com.aristiec.schoolmanagementsystem.controller.staff;

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
import org.springframework.web.server.ResponseStatusException;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;
import com.aristiec.schoolmanagementsystem.dto.staff.StaffDTO;
import com.aristiec.schoolmanagementsystem.modal.staff.Staff;
import com.aristiec.schoolmanagementsystem.service.staff.StaffService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/staff")

@SecurityRequirement(name = "bearerAuth") 
public class StaffController {
      @Autowired
    private StaffService staffService;

    @PostMapping
    public ResponseEntity<Staff> addStaff(@RequestBody StaffDTO staffDTO) {
        Staff savedStaff = staffService.addStaff(staffDTO);
        return ResponseEntity.ok(savedStaff);
    }

     @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }

     @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody StaffDTO staffDTO) {
        Staff updatedStaff = staffService.updateStaff(id, staffDTO);
        return ResponseEntity.ok(updatedStaff);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff deleted with id: " + id);
    }

    @GetMapping("/department/{departmentId}")
  public ResponseEntity<List<Staff>> getStaffByDepartment(@PathVariable Long departmentId) {
    List<Staff> staffList = staffService.getstaffByDepartment(departmentId);
    return ResponseEntity.ok(staffList);
}



@GetMapping("/designation/{designation}")
public List<Staff> getStaffByDesignation(@PathVariable String designation) {
    try {
        StaffDesignation enumValue = StaffDesignation.valueOf(designation.toUpperCase());
        return staffService.findByDesignation(enumValue);
    } catch (IllegalArgumentException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid designation: " + designation);
    }
}


    
}
