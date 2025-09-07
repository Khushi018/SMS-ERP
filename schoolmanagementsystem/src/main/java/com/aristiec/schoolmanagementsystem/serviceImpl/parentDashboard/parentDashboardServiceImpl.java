package com.aristiec.schoolmanagementsystem.serviceImpl.parentDashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.dto.AssignmentDTO;
import com.aristiec.schoolmanagementsystem.dto.AttendDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.FeesDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.ParentDashboardDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.StudentDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.TotalAttendanceDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;
import com.aristiec.schoolmanagementsystem.repository.assignment.AssignmentRepository;
import com.aristiec.schoolmanagementsystem.repository.attendance.AttendanceRepository;
import com.aristiec.schoolmanagementsystem.repository.details.FeesRepository;
import com.aristiec.schoolmanagementsystem.repository.details.ParentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.subject.SubjectRepository;
import com.aristiec.schoolmanagementsystem.service.parentDashboard.ParentDashboardService;

@Service
public class parentDashboardServiceImpl implements ParentDashboardService {
     @Autowired
    private ParentDetailsRepository parentDetailsRepository;
     @Autowired
    private StudentDetailsRepository studentDetailsRepository;
     @Autowired
    private FeesRepository feesRepository;
     @Autowired
    private AttendanceRepository attendanceRepository;
     @Autowired
    private AssignmentRepository assignmentRepository;
      
    @Autowired
    private SubjectRepository subjectRepository;

     @Override
     public ParentDashboardDTO getParentDashboardDTO(Long parentId, Long studentId) {
       
        ParentDetails parentDetails=parentDetailsRepository.findById(parentId)
                 .orElseThrow(()->new RuntimeException("Parent not found"));

        StudentDetails studentDetails=studentDetailsRepository.findById(studentId)
        .orElseThrow(()->new RuntimeException("Student not found"));

        if(studentDetails.getParentDetails()==null || !studentDetails.getParentDetails().getId().equals(parentId)){
            throw new RuntimeException("Student does not belong to this Parent");

        }

        StudentDTO studentDTO=new StudentDTO();
        studentDTO.setId(studentDetails.getId());
        studentDTO.setFirstName(studentDetails.getFirstName());
        studentDTO.setLastName(studentDetails.getLastName());
        studentDTO.setEmail(studentDetails.getEmail());
        studentDTO.setCourseName(studentDetails.getCourse().getCourseName());


        List<FeesDTO> fees = feesRepository.findByStudentDetailsId(studentId).stream().map(fee -> {
            FeesDTO dto = new FeesDTO();
            dto.setAmount(fee.getAmount());
            dto.setPaymentStatus(fee.getPaymentStatus().name());
            dto.setPaymentDate(fee.getPaymentDate());
            return dto;
        }).toList();

    long totalDays = attendanceRepository.countTotalByStudentId(studentId);
    long presentDays = attendanceRepository.countPresentByStudentId(studentId);

    double percentage = totalDays > 0 ? (presentDays * 100.0 / totalDays) : 0;

    TotalAttendanceDTO totalAttendance = new TotalAttendanceDTO(totalDays, presentDays, percentage);


         ParentDashboardDTO dashboardDTO = new ParentDashboardDTO();
        dashboardDTO.setStudent(studentDTO);
        dashboardDTO.setFees(fees);
        dashboardDTO.setTotalAttendance(totalAttendance);

        return dashboardDTO;

     }


      @Override
      public List<AssignmentDTO> getAssignmentsForStudent(Long parentId, Long studentId) {
     StudentDetails student = studentDetailsRepository.findById(studentId)
             .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

     if (!student.getParentDetails().getId().equals(parentId)) {
         throw new RuntimeException("Student does not belong to the given parent");
     }

    //  List<Subject> subjects = subjectRepository.findByStudentId(studentId);

      List<AssignmentDTO> assignmentDTOs = new ArrayList<>();

    //  for (Subject subject : subjects) {
    //      for (Assignment assignment : subject.getAssignment()) {
    //          AssignmentDTO dto = new AssignmentDTO();
    //          dto.setId(assignment.getId());
    //          dto.setTitle(assignment.getTitle());
    //          dto.setDescription(assignment.getDescription());
    //          dto.setDueDate(assignment.getDueDate());
    //         //  dto.setSubjectName(subject.getName());
    //          assignmentDTOs.add(dto);
    //      }
    //  }

     return assignmentDTOs;

   }

      @Override
      public TotalAttendanceDTO getTotalAttendanceByStudent(Long studentId) {
            long totalDays = attendanceRepository.countTotalByStudentId(studentId);
        long presentDays = attendanceRepository.countPresentByStudentId(studentId);

        double percentage = totalDays > 0 ? (presentDays * 100.0 / totalDays) : 0;

        return new TotalAttendanceDTO(totalDays, presentDays, percentage);
    }

      @Override
      public List<SubjectAttendanceDTO> getSubjectAttendanceByStudent(Long studentId) {
          List<Attendance> attendances = attendanceRepository.findByStudentDetailsId(studentId);

    if (attendances.isEmpty()) {
        throw new RuntimeException("No attendance records found for student ID: " + studentId);
    }

    return attendances.stream()
            .filter(a -> a.getTimetable() != null && a.getTimetable().getSubject() != null)
            .collect(Collectors.groupingBy(a -> a.getTimetable().getSubject().getName()))
            .entrySet().stream()
            .map(entry -> {
                String subjectName = entry.getKey();
                List<Attendance> records = entry.getValue();
                long total = records.size();
                long present = records.stream()
                        .filter(a -> a.getAttendanceStatus() == AttendanceStatus.PRESENT)
                        .count();

                SubjectAttendanceDTO dto = new SubjectAttendanceDTO();
                dto.setSubjectName(subjectName);
                dto.setTotalDays(total);
                dto.setPresentDays(present);
                dto.setAttendancePercentage((present * 100.0) / total);

                return dto;
            })
            .collect(Collectors.toList());

    }

}