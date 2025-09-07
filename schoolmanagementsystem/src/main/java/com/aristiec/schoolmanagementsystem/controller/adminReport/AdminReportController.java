package com.aristiec.schoolmanagementsystem.controller.adminReport;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.adminReport.ActiveLibraryUsersCountDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.AdminReportDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.AttendancePercentageDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.BookIssuedReturnedPerMonthDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.DemographicStatDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.EnrollmentTrendDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.FeeReportDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.FeeStatusDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.HostelRoomBedStatsDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.HostelTypeCountDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.MostBorrowedBookDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.OccupancyStatusDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.OverdueBooksAndFinesDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.StaffFacultyCountDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.DepartmentStaffFacultyCountDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.DesignationCountDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.StudentCountByCourseDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.TotalBooksDTO;
import com.aristiec.schoolmanagementsystem.dto.adminReport.TransportSummaryDTO;
import com.aristiec.schoolmanagementsystem.service.adminReport.AdminreportService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/report/")
@Tag(name = "Admin Report Management", description = "Manage various reports for administrative purposes")
@SecurityRequirement(name = "bearerAuth")
public class AdminReportController {
    @Autowired
    private AdminreportService adminreportService;

    @GetMapping("/overview")
    public AdminReportDTO getOverview() {
        return adminreportService.getAdminReportDTO();
    }

    @GetMapping("/student-count-by-course")
    public ResponseEntity<List<StudentCountByCourseDTO>> getStudentCountByCourse() {
        return ResponseEntity.ok(adminreportService.getStudentCountByCourse());
    }

    @GetMapping("/gender")
    public ResponseEntity<List<DemographicStatDTO>> getGenderDistribution() {
        return ResponseEntity.ok(adminreportService.getGenderDistribution());
    }

    @GetMapping("/age-group")
    public ResponseEntity<List<DemographicStatDTO>> getAgeGroupDistribution() {
        return ResponseEntity.ok(adminreportService.getAgeGroupDistribution());
    }

    @GetMapping("/enrollment-trends")
    public ResponseEntity<List<EnrollmentTrendDTO>> getEnrollmentTrends() {
        return ResponseEntity.ok(adminreportService.getEnrollmentTrends());
    }

    @GetMapping("/percentage")
    public List<AttendancePercentageDTO> getAttendancePercentages() {
        return adminreportService.getAverageAttendanceByCourse();
    }

    @GetMapping("/fee-status-counts")
    public ResponseEntity<FeeStatusDTO> getFeeStatusCounts() {
        return ResponseEntity.ok(adminreportService.getFeeStatusCounts());
    }

    @GetMapping("/staff-faculty-department-wise")
    public ResponseEntity<List<DepartmentStaffFacultyCountDTO>> getStaffCountByDepartment() {
        List<DepartmentStaffFacultyCountDTO> result = adminreportService.getStaffFacultyCountByDepartment();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/staff-faculty-designation-wise")
    public ResponseEntity<Map<String, List<DesignationCountDTO>>> getStaffCountByDesignation() {
        Map<String, List<DesignationCountDTO>> result = adminreportService.getCountsByDesignation();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/total-books")
    public ResponseEntity<TotalBooksDTO> getTotalBooksAndCopies() {
        TotalBooksDTO dto = adminreportService.getTotalBooksAndCopies();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/books-issued-returned-per-month")
    public ResponseEntity<List<BookIssuedReturnedPerMonthDTO>> getBooksIssuedReturnedPerMonth() {
        List<BookIssuedReturnedPerMonthDTO> dtos = adminreportService.getBooksIssuedReturnedPerMonth();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/overdue-books-and-fines")
    public ResponseEntity<OverdueBooksAndFinesDTO> getOverdueBooksAndFines() {
        return ResponseEntity.ok(adminreportService.getOverdueBooksAndFines());
    }

    @GetMapping("/most-borrowed-books")
    public List<MostBorrowedBookDTO> getMostBorrowedBooks() {
        return adminreportService.getMostBorrowedBooks();
    }

    @GetMapping("/active-library-users-count")
    public ResponseEntity<ActiveLibraryUsersCountDTO> getActiveLibraryUsersCount() {
        ActiveLibraryUsersCountDTO dto = adminreportService.getActiveLibraryUsersCount();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/hostels/room-bed-stats")
    public ResponseEntity<HostelRoomBedStatsDTO> getRoomBedStats() {
        return ResponseEntity.ok(adminreportService.getTotalRoomAndBedStats());
    }

    @GetMapping("/hostels/occupancy-status")
    public ResponseEntity<OccupancyStatusDTO> getOccupancyStatus() {
        return ResponseEntity.ok(adminreportService.getOccupancyStatus());
    }

    @GetMapping("/hostels/type-count")
    public ResponseEntity<HostelTypeCountDTO> getHostelTypeCount() {
        return ResponseEntity.ok(adminreportService.getHostelTypeCounts());
    }

    @GetMapping("/transport/summary")
    public ResponseEntity<TransportSummaryDTO> getSummary() {
        TransportSummaryDTO dto = adminreportService.getTransportSummary();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/staff-faculty-count")
    public StaffFacultyCountDTO getAdminReport() {
        return adminreportService.getTotalStaffFaculty();
    }

    @GetMapping("/total-fee")
    public ResponseEntity<FeeReportDTO> getTotalFee() {
        return ResponseEntity.ok(adminreportService.getTotalFeeReport());
    }

    @GetMapping("/total-unpaid-fee")
    public ResponseEntity<FeeReportDTO> getTotalUnpaidFee() {
        return ResponseEntity.ok(adminreportService.getTotalUnpaidFeeReport());
    }

}
