package com.aristiec.schoolmanagementsystem.serviceImpl.attendance;


import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.dto.AttendDTO;
import com.aristiec.schoolmanagementsystem.dto.OverallAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.attendance.AttendanceResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.attendance.SubjectwiseAttendanceDTO;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.attendance.Attendance;
import com.aristiec.schoolmanagementsystem.modal.timetable.Timetable;
import com.aristiec.schoolmanagementsystem.repository.attendance.AttendanceRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.timetable.TimetableRepository;
import com.aristiec.schoolmanagementsystem.service.attendance.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public AttendanceResponseDTO getAttendanceById(Long id) {
        Attendance a = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        return toResponseDTO(a);
    }

    @Override
    public List<AttendanceResponseDTO> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteAttendance(Long id) {
        if (attendanceRepository.existsById(id)) {
            attendanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public String updateAttendance(Long id, AttendDTO dto) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        StudentDetails student = studentDetailsRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Timetable timetable = timetableRepository.findById(dto.getTimetableId())
                .orElseThrow(() -> new RuntimeException("Timetable not found"));

        attendance.setStudentDetails(student);
        attendance.setTimetable(timetable);
        attendance.setDate(dto.getDate());
        attendance.setAttendanceStatus(dto.getAttendanceStatus());

        attendanceRepository.save(attendance);
        return "Attendance updated successfully.";

    }

    @Override
    public void markAttendance(List<AttendDTO> requestList) {
        for (AttendDTO dto : requestList) {

            boolean exists = attendanceRepository.existsByStudentDetailsIdAndTimetableIdAndDate(
                    dto.getStudentId(), dto.getTimetableId(), dto.getDate());

            if (!exists) {
                StudentDetails student = studentDetailsRepository.findById(dto.getStudentId())
                        .orElseThrow(() -> new RuntimeException("Student not found"));

                Timetable timetable = timetableRepository.findById(dto.getTimetableId())
                        .orElseThrow(() -> new RuntimeException("Timetable not found"));

                Attendance attendance = new Attendance();
                attendance.setStudentDetails(student);
                attendance.setTimetable(timetable);
                attendance.setDate(dto.getDate());
                attendance.setAttendanceStatus(dto.getAttendanceStatus());

                attendanceRepository.save(attendance);
            }
        }

    }

    @Override
    public OverallAttendanceDTO getOverallAttendance(Long studentId) {

        List<Attendance> attendances = attendanceRepository.findByStudentDetailsId(studentId);

        if (attendances.isEmpty()) {
            throw new RuntimeException("No attendance records found for student ID: " + studentId);
        }

        long total = attendances.size();
        long present = attendances.stream()
                .filter(a -> a.getAttendanceStatus() == AttendanceStatus.PRESENT)
                .count();

        long missed = total - present;
        StudentDetails student = attendances.get(0).getStudentDetails(); // reused from attendance

        OverallAttendanceDTO dto = new OverallAttendanceDTO();
        dto.setStudentId(studentId);
        dto.setStudentName(student.getFirstName() + " " + student.getLastName());
        dto.setPresentLectures(present);
        dto.setTotalLectures(total);
        dto.setMissedLectures(missed);
        dto.setAttendancePercentage((present * 100.0) / total);

        return dto;

    }

    @Override
    public List<SubjectwiseAttendanceDTO> getSubjectWiseAttendance(Long studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentDetailsId(studentId);

        if (attendances.isEmpty()) {
            throw new RuntimeException("No attendance records found for student ID: " + studentId);
        }

        // Group by Subject ID (to access both ID and name)
        Map<Long, List<Attendance>> groupedBySubjectId = attendances.stream()
                .filter(a -> a.getTimetable() != null && a.getTimetable().getSubject() != null)
                .collect(Collectors.groupingBy(a -> a.getTimetable().getSubject().getId()));

        return groupedBySubjectId.entrySet().stream()
                .map(entry -> {
                    Long subjectId = entry.getKey();
                    List<Attendance> records = entry.getValue();

                    String subjectName = records.get(0).getTimetable().getSubject().getName(); // assume consistent
                    long total = records.size();
                    long present = records.stream()
                            .filter(a -> a.getAttendanceStatus() == AttendanceStatus.PRESENT)
                            .count();
                    long missed = total - present;

                    return new SubjectwiseAttendanceDTO(
                            subjectId,
                            subjectName,
                            present,
                            total,
                            missed,
                            (present * 100.0) / total);
                })
                .collect(Collectors.toList());

    }

    @Override
    public OverallAttendanceDTO getMonthlyOverallAttendance(Long studentId, int month, int year) {

        List<Attendance> attendances = attendanceRepository.findByStudentDetailsId(studentId);

        if (attendances.isEmpty()) {
            throw new RuntimeException("No attendance records found for student ID: " + studentId);
        }

        List<Attendance> filtered = attendances.stream()
                .filter(a -> a.getDate().getMonthValue() == month && a.getDate().getYear() == year)
                .toList();

        long total = filtered.size();
        long present = filtered.stream()
                .filter(a -> a.getAttendanceStatus() == AttendanceStatus.PRESENT)
                .count();

        StudentDetails student = attendances.get(0).getStudentDetails();

        OverallAttendanceDTO dto = new OverallAttendanceDTO();
        dto.setStudentId(studentId);
        dto.setStudentName(student.getFirstName() + " " + student.getLastName());
        dto.setTotalLectures(total);
        dto.setPresentLectures(present);
        dto.setAttendancePercentage(total == 0 ? 0.0 : (present * 100.0) / total);

        return dto;

    }

    private AttendanceResponseDTO toResponseDTO(Attendance attendance) {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setId(attendance.getId());
        dto.setStudentId(attendance.getStudentDetails().getId());
        dto.setStudentName(attendance.getStudentDetails().getFirstName() + " " +
                attendance.getStudentDetails().getLastName());
        dto.setTimetableId(attendance.getTimetable().getId());
        dto.setSubjectName(attendance.getTimetable().getSubject().getName());
        dto.setDate(attendance.getDate());
        dto.setAttendanceStatus(attendance.getAttendanceStatus());
        return dto;
    }

}
