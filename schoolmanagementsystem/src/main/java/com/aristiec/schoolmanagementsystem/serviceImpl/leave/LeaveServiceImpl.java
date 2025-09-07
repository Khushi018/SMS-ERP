package com.aristiec.schoolmanagementsystem.serviceImpl.leave;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.DesignationEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveReasonEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.LeaveStatus;
import com.aristiec.schoolmanagementsystem.dto.leave.LeaveDTO;
import com.aristiec.schoolmanagementsystem.dto.leave.LeaveResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.leave.Leave;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.faculty.FacultyRepository;
import com.aristiec.schoolmanagementsystem.repository.leave.LeaveRepository;
import com.aristiec.schoolmanagementsystem.repository.staff.StaffRepository;
import com.aristiec.schoolmanagementsystem.service.leave.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {
     @Autowired
    private LeaveRepository leaveRepository;
      
    @Autowired
     private  StudentDetailsRepository studentRepo;
     @Autowired
    private  FacultyRepository facultyRepo;
    @Autowired
    private  StaffRepository staffRepo;
     

    @Override
    public String applyLeave(LeaveDTO dto) {
      Leave leave=new Leave();
        leave.setUserId(dto.getUserId());
        leave.setDesignation(dto.getDesignation());
        leave.setLeaveReason(dto.getLeaveReason());
        leave.setFromDate(LocalDate.parse(dto.getFromDate()));
        leave.setToDate(LocalDate.parse(dto.getToDate()));
        leave.setAppliDate(LocalDate.now());
        leave.setStatus(LeaveStatus.PENDING);

        leaveRepository.save(leave);

        return "Leave request submitted successfully.";

    }

    @Override
    public List<LeaveResponseDTO> getMyLeaves(Long userId, DesignationEnum designation) {
                 return leaveRepository.findByUserIdAndDesignation(userId, designation).stream()
                .map(this::toResponseDTO)
                .toList();

    }

    @Override
    public List<LeaveResponseDTO> getStudentLeaves() {
       return leaveRepository.findAll().stream()
                .filter(l -> l.getDesignation() == DesignationEnum.STUDENT)
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public String updateStudentLeaveStatus(Long id, LeaveStatus status) {
          Leave leave = leaveRepository.findById(id).orElseThrow();
        if (leave.getDesignation() != DesignationEnum.STUDENT)
            throw new IllegalArgumentException("Only student leave can be updated here");
        leave.setStatus(status);
        leaveRepository.save(leave);
        return "Student leave status updated.";
    }

    @Override
    public List<LeaveResponseDTO> getFacultyAndStaffLeaves() {
        return leaveRepository.findAll().stream()
                .filter(l -> l.getDesignation() != DesignationEnum.STUDENT)
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public String updateFacultyOrStaffLeaveStatus(Long id, LeaveStatus status) {
        Leave leave = leaveRepository.findById(id).orElseThrow();
        if (leave.getDesignation() == DesignationEnum.STUDENT)
            throw new IllegalArgumentException("Admin cannot update student leave");
        leave.setStatus(status);
        leaveRepository.save(leave);
        return "leave status updated.";
    }

    
   
     private LeaveResponseDTO toResponseDTO(Leave leave) {
        String name = switch (leave.getDesignation()) {
            case STUDENT -> studentRepo.findById(leave.getUserId())
                    .map(s -> s.getFirstName() + " " + s.getLastName()).orElse("Unknown Student");
            case PROFESSOR, ASSISTANT_PROFESSOR, LECTURER, HOD, DEAN, ADJUNCT_PROFESSOR ->
                    facultyRepo.findById(leave.getUserId()).map(f -> f.getFullName()).orElse("Unknown Faculty");
            default -> staffRepo.findById(leave.getUserId()).map(s -> s.getFullName()).orElse("Unknown Staff");
        };

        return new LeaveResponseDTO(
                leave.getId(),
                name,
                leave.getDesignation(),
                leave.getLeaveReason(),
                leave.getFromDate().toString(),
                leave.getToDate().toString(),
                leave.getAppliDate().toString(),
                leave.getStatus()
        );
   
}

     @Override
     public List<LeaveResponseDTO> getFacultyOrStaffLeavesByDesignation(DesignationEnum designation) {
             if (designation == DesignationEnum.STUDENT) {
        throw new IllegalArgumentException("Admin cannot access student leave requests");
    }

    return leaveRepository.findByDesignation(designation).stream()
            .map(this::toResponseDTO).toList();
    }

     @Override
     public List<LeaveResponseDTO> getFacultyOrStaffLeavesByStatus(LeaveStatus status) {
            return leaveRepository.findByStatus(status).stream()
            .filter(leave -> leave.getDesignation() != DesignationEnum.STUDENT)
            .map(this::toResponseDTO).toList();
}
    

     @Override
     public List<LeaveResponseDTO> getFacultyOrStaffLeavesByReason(LeaveReasonEnum reason) {
             return leaveRepository.findByLeaveReason(reason).stream()
            .filter(leave -> leave.getDesignation() != DesignationEnum.STUDENT)
            .map(this::toResponseDTO).toList();
    }

     @Override
     public List<LeaveResponseDTO> getFacultyOrStaffLeavesByAppliedDate(LocalDate date) {
             return leaveRepository.findByAppliDate(date).stream()
            .filter(leave -> leave.getDesignation() != DesignationEnum.STUDENT)
            .map(this::toResponseDTO).toList();
    }

     @Override
     public List<LeaveResponseDTO> getFacultyOrStaffLeavesByStatusAndDesignation(LeaveStatus status,
            DesignationEnum designation) {
              if (designation == DesignationEnum.STUDENT) {
        throw new IllegalArgumentException("Admin cannot access student leave requests");
    }

    return leaveRepository.findByStatusAndDesignation(status, designation).stream()
            .map(this::toResponseDTO).toList();

            }

     @Override
     public List<LeaveResponseDTO> getStudentLeavesByStatus(LeaveStatus status) {
          return leaveRepository.findByStatus(status).stream()
            .filter(leave -> leave.getDesignation() == DesignationEnum.STUDENT)
            .map(this::toResponseDTO).toList();
    }

     @Override
     public List<LeaveResponseDTO> getStudentLeavesByReason(LeaveReasonEnum reason) {
              return leaveRepository.findByLeaveReason(reason).stream()
            .filter(leave -> leave.getDesignation() == DesignationEnum.STUDENT)
            .map(this::toResponseDTO).toList();
    }

     @Override
     public List<LeaveResponseDTO> getStudentLeavesByAppliedDate(LocalDate appliedDate) {
                 return leaveRepository.findByAppliDate(appliedDate).stream()
            .filter(leave -> leave.getDesignation() == DesignationEnum.STUDENT)
            .map(this::toResponseDTO).toList();
    }

     @Override
     public List<LeaveResponseDTO> getStudentLeavesByStatusAndDesignation(LeaveStatus status,
            DesignationEnum designation) {
           if (designation != DesignationEnum.STUDENT) {
        throw new IllegalArgumentException("Faculty can only access student leave requests.");
    }

    return leaveRepository.findByStatusAndDesignation(status, designation).stream()
            .map(this::toResponseDTO).toList();
            }

 }
