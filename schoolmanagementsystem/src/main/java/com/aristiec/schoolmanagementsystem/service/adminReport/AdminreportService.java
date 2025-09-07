package com.aristiec.schoolmanagementsystem.service.adminReport;

import java.util.List;
import java.util.Map;

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

public interface AdminreportService {
    AdminReportDTO getAdminReportDTO();
    List<StudentCountByCourseDTO> getStudentCountByCourse();
     List<DemographicStatDTO> getGenderDistribution();
     List<DemographicStatDTO> getAgeGroupDistribution();
     List<EnrollmentTrendDTO> getEnrollmentTrends();
     List<AttendancePercentageDTO> getAverageAttendanceByCourse();
      FeeStatusDTO getFeeStatusCounts();
      List<DepartmentStaffFacultyCountDTO> getStaffFacultyCountByDepartment();
     TotalBooksDTO getTotalBooksAndCopies();
      List<BookIssuedReturnedPerMonthDTO> getBooksIssuedReturnedPerMonth();
      OverdueBooksAndFinesDTO getOverdueBooksAndFines();
      List<MostBorrowedBookDTO> getMostBorrowedBooks();
      ActiveLibraryUsersCountDTO getActiveLibraryUsersCount();
      HostelRoomBedStatsDTO getTotalRoomAndBedStats();
      OccupancyStatusDTO getOccupancyStatus();
      HostelTypeCountDTO getHostelTypeCounts();
      TransportSummaryDTO getTransportSummary();
      StaffFacultyCountDTO getTotalStaffFaculty();
      Map<String, List<DesignationCountDTO>> getCountsByDesignation();
       FeeReportDTO getTotalFeeReport();
       FeeReportDTO getTotalUnpaidFeeReport();

  
    }
