package com.aristiec.schoolmanagementsystem.serviceImpl.adminReport;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.constant.enums.FacultyDesignation;
import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;
import com.aristiec.schoolmanagementsystem.constant.enums.PaymentStatus;
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
import com.aristiec.schoolmanagementsystem.modal.admission.Fee;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;
import com.aristiec.schoolmanagementsystem.modal.hostel.Hostel;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomAllocations;
import com.aristiec.schoolmanagementsystem.modal.hostel.RoomDetail;
import com.aristiec.schoolmanagementsystem.repository.attendance.AttendanceRepository;
import com.aristiec.schoolmanagementsystem.repository.department.DepartmentRepository;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.FeesRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.FacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.HostelRepository;
import com.aristiec.schoolmanagementsystem.repository.hostel.RoomDetailRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookCopyRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BookRepository;
import com.aristiec.schoolmanagementsystem.repository.library.BorrowRecordRepository;
import com.aristiec.schoolmanagementsystem.repository.staff.StaffRepository;
import com.aristiec.schoolmanagementsystem.service.adminReport.AdminreportService;
import com.aristiec.schoolmanagementsystem.transport.repository.DriverRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.RouteRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StoppageRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StudentTransportAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.VehicleRepository;

@Service
public class AdminReportServiceImpl implements AdminreportService {
      
     @Autowired
     private StudentDetailsRepository studentDetailsRepository;
      
     @Autowired
     private StaffRepository staffRepository;
      
     @Autowired
     private FacultyRepository facultyRepository;
       
     @Autowired
      private DepartmentRepository departmentRepository;
       @Autowired
      private CourseRepository courseRepository;
        @Autowired
       private AttendanceRepository attendanceRepository;
        
       @Autowired
       private FeesRepository feesRepository;
       
       @Autowired
       private BookRepository bookRepository;
        
       @Autowired
       private BookCopyRepository bookCopyRepository;
        
       @Autowired
       private BorrowRecordRepository borrowRecordRepository;
        
       @Autowired
       private RoomDetailRepository roomDetailRepository;
       
        @Autowired
       private HostelRepository hostelRepository;

        @Autowired
       private VehicleRepository vehicleRepository;
        
       @Autowired
       private DriverRepository driverRepository;

        @Autowired
    private StudentTransportAssignmentRepository studentAssignmentRepository;

    @Autowired
    private StoppageRepository stoppageRepository;


    @Autowired
    private RouteRepository routeRepository;

    @Override
    public AdminReportDTO getAdminReportDTO() {
       long students=studentDetailsRepository.count();
        long totalTeachingStaff = facultyRepository.count();
      long totalNonTeachingStaff = staffRepository.count();
       long department=departmentRepository.count();
       long courses=courseRepository.count();

       return new AdminReportDTO(students, totalTeachingStaff, totalNonTeachingStaff, department, courses);
    }


    @Override
    public List<StudentCountByCourseDTO> getStudentCountByCourse() {
      return studentDetailsRepository.countStudentsByCourse();
    }


    @Override
    public List<DemographicStatDTO> getGenderDistribution() {
      return convertToDTOList(studentDetailsRepository.countByGender());
    }

       @Override
     public List<DemographicStatDTO> getAgeGroupDistribution() {
      return convertToDTOList(studentDetailsRepository.countByAgeGroupNative());

    }
   

     private List<DemographicStatDTO> convertToDTOList(List<Object[]> data) {
        return data.stream()
                .map(obj -> new DemographicStatDTO((String) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }


     @Override
     public List<EnrollmentTrendDTO> getEnrollmentTrends() {
          return studentDetailsRepository.getEnrollmentTrends();

    }


     


     @Override
     public FeeStatusDTO getFeeStatusCounts() {
          List<Fee> allFees = feesRepository.findAll();

    Map<Long, Fee> latestFeeByStudent = allFees.stream()
        .collect(Collectors.groupingBy(
            fee -> fee.getStudentDetails().getId(),
            Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparing(Fee::getPaymentDate)),
                Optional::get
            )
        ));

    long paidCount = latestFeeByStudent.values().stream()
        .filter(fee -> fee.getPaymentStatus() == PaymentStatus.PAID)
        .count();

    long unpaidCount = latestFeeByStudent.size() - paidCount;

    return new FeeStatusDTO(paidCount, unpaidCount);

    }


     @Override
     public List<AttendancePercentageDTO> getAverageAttendanceByCourse() {

    List<Attendance> allAttendance = attendanceRepository.findAll();

    Map<String, List<Attendance>> courseAttendanceMap = allAttendance.stream()
        .filter(a -> a.getStudentDetails() != null )
        .collect(Collectors.groupingBy(a -> a.getStudentDetails().getCourse().getCourseName()));

    List<AttendancePercentageDTO> result = new ArrayList<>();

    for (Map.Entry<String, List<Attendance>> entry : courseAttendanceMap.entrySet()) {
        String courseName = entry.getKey();
        List<Attendance> attendanceList = entry.getValue();

        long present = attendanceList.stream()
            .filter(a -> a.getAttendanceStatus() == AttendanceStatus.PRESENT)
            .count();

        long total = attendanceList.size();

        double percentage = total > 0 ? ((double) present / total) * 100 : 0;

        result.add(new AttendancePercentageDTO(courseName, percentage));
    }

    return result;

    }


    




     @Override
     public TotalBooksDTO getTotalBooksAndCopies() {
        long totaBooks=bookRepository.count();
        long totalBookCopies=bookCopyRepository.count();

        return new TotalBooksDTO(totaBooks,totalBookCopies);

    }


     @Override
     public List<BookIssuedReturnedPerMonthDTO> getBooksIssuedReturnedPerMonth() {

        List<Object[]> issuedData = borrowRecordRepository.countIssuedBooksPerMonth();
        List<Object[]> returnedData = borrowRecordRepository.countReturnedBooksPerMonth();

        Map<String, Long> issuedMap = issuedData.stream()
            .collect(Collectors.toMap(
                row -> (String) row[0],
                row -> ((Number) row[1]).longValue()
            ));

        Map<String, Long> returnedMap = returnedData.stream()
            .collect(Collectors.toMap(
                row -> (String) row[0],
                row -> ((Number) row[1]).longValue()
            ));

        Set<String> months = new HashSet<>();
        months.addAll(issuedMap.keySet());
        months.addAll(returnedMap.keySet());

        List<String> sortedMonths = new ArrayList<>(months);
        Collections.sort(sortedMonths);

        List<BookIssuedReturnedPerMonthDTO> result = new ArrayList<>();

        for (String month : sortedMonths) {
            long issuedCount = issuedMap.getOrDefault(month, 0L);
            long returnedCount = returnedMap.getOrDefault(month, 0L);
            result.add(new BookIssuedReturnedPerMonthDTO(month, issuedCount, returnedCount));
        }

        return result;

    }


     @Override
     public OverdueBooksAndFinesDTO getOverdueBooksAndFines() {
     List<Object[]> result = borrowRecordRepository.findOverdueBooksAndTotalFine();

        if (result.isEmpty()) {
            return new OverdueBooksAndFinesDTO(0L, 0.0);
        }

        Object[] row = result.get(0);

        Long overdueCount = row[0] != null ? ((Number) row[0]).longValue() : 0L;
        Double totalFine = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;

        return new OverdueBooksAndFinesDTO(overdueCount, totalFine);

    }


     @Override
     public List<MostBorrowedBookDTO> getMostBorrowedBooks() {
      return borrowRecordRepository.findMostBorrowedBooks();

    }


     @Override
     public ActiveLibraryUsersCountDTO getActiveLibraryUsersCount() {
        Long count = borrowRecordRepository.countDistinctActiveUsers();
        return new ActiveLibraryUsersCountDTO(count);

    }


     @Override
     public HostelRoomBedStatsDTO getTotalRoomAndBedStats() {
         List<RoomDetail> allRooms = roomDetailRepository.findAll();

    int totalRooms = allRooms.size();

    int totalBeds = allRooms.stream()
                            .mapToInt(RoomDetail::getStudentPerRoom)
                            .sum();

    int totalHostels = (int) allRooms.stream()
                            .filter(r -> r.getHostel() != null) 
                            .map(r -> r.getHostel().getHostelId())
                            .distinct()
                            .count();

    HostelRoomBedStatsDTO dto = new HostelRoomBedStatsDTO();
    dto.setTotalHostels(totalHostels);
    dto.setTotalRooms(totalRooms);
    dto.setTotalBeds(totalBeds);

    return dto;


    }


     @Override
     public OccupancyStatusDTO getOccupancyStatus() {
        List<RoomDetail> allRooms = roomDetailRepository.findAll();

    int occupiedRooms = 0;
    int occupiedBeds = 0;

    for (RoomDetail room : allRooms) {
        List<RoomAllocations> allocations = room.getRoomAllocations();

        if (allocations != null && !allocations.isEmpty()) {
            long activeAllocations = allocations.stream()
                .filter(a -> !a.isVacated())
                .count();

            if (activeAllocations > 0) {
                occupiedRooms++;
                occupiedBeds += activeAllocations;
            }
        }
    }

    int totalRooms = allRooms.size();
    int totalBeds = allRooms.stream()
        .mapToInt(RoomDetail::getStudentPerRoom)
        .sum();

    OccupancyStatusDTO dto = new OccupancyStatusDTO();
    dto.setOccupiedRooms(occupiedRooms);
    dto.setVacantRooms(totalRooms - occupiedRooms);
    dto.setOccupiedBeds(occupiedBeds);
    dto.setVacantBeds(totalBeds - occupiedBeds);

    return dto;


    }


     @Override
     public HostelTypeCountDTO getHostelTypeCounts() {
       List<Hostel> allHostels = hostelRepository.findAll();

    int boyCount = (int) allHostels.stream()
        .filter(h -> "BOYS".equalsIgnoreCase(h.getHostelType()))
        .count();

    int girlCount = (int) allHostels.stream()
        .filter(h -> "GIRLS".equalsIgnoreCase(h.getHostelType()))
        .count();

    return new HostelTypeCountDTO(boyCount, girlCount);


    }


    


    


     @Override
     public TransportSummaryDTO getTransportSummary() {
        long totalDrivers = driverRepository.count();
        long totalStudents = studentAssignmentRepository.count();
        long totalStoppages = stoppageRepository.count();
        long totalVehicles = vehicleRepository.count();
        long totalRoutes = routeRepository.count();

        return new TransportSummaryDTO(
            totalDrivers,
            totalStudents,
            totalStoppages,
            totalVehicles,
            totalRoutes
        );
    }


     @Override
     public StaffFacultyCountDTO getTotalStaffFaculty() {
       long staffCount = staffRepository.count();
        long facultyCount = facultyRepository.count();
        return new StaffFacultyCountDTO(staffCount, facultyCount);

    }


     @Override
     public List<DepartmentStaffFacultyCountDTO> getStaffFacultyCountByDepartment() {
          return departmentRepository.findAll().stream().map(dept -> {
        long staffCount = staffRepository.countByDepartmentId(dept.getId());
        long facultyCount = facultyRepository.countByDepartmentId(dept.getId());
        return new DepartmentStaffFacultyCountDTO(dept.getName(), staffCount, facultyCount);
    }).toList();
    
    }


     @Override
     public Map<String, List<DesignationCountDTO>> getCountsByDesignation() {
         List<DesignationCountDTO> staffCounts = Arrays.stream(StaffDesignation.values())
        .map(designation -> new DesignationCountDTO(
            designation.name(),
            staffRepository.countByDesignation(designation)
        )).toList();

    List<DesignationCountDTO> facultyCounts = Arrays.stream(FacultyDesignation.values())
        .map(designation -> new DesignationCountDTO(
            designation.name(),
            facultyRepository.countByPosition(designation)
        )).toList();

    Map<String, List<DesignationCountDTO>> result = new HashMap<>();
    result.put("staff", staffCounts);
    result.put("faculty", facultyCounts);
    return result;
    
    }


     @Override
     public FeeReportDTO getTotalFeeReport() {
        BigDecimal totalAmount = feesRepository.getTotalFeeAmount();
        return new FeeReportDTO(totalAmount != null ? totalAmount : BigDecimal.ZERO);

    }


     @Override
     public FeeReportDTO getTotalUnpaidFeeReport() {
         Double totalCourseFee = studentDetailsRepository.getTotalCourseFeeByAllStudents(); 
        BigDecimal totalPaidFee = feesRepository.getTotalFeeAmount(); 

        BigDecimal courseFee = totalCourseFee != null ? BigDecimal.valueOf(totalCourseFee) : BigDecimal.ZERO;
        BigDecimal paidFee = totalPaidFee != null ? totalPaidFee : BigDecimal.ZERO;

        BigDecimal unpaid = courseFee.subtract(paidFee).max(BigDecimal.ZERO); 

        return new FeeReportDTO(unpaid);

    } 


     



    }


    

     

    


    


    

