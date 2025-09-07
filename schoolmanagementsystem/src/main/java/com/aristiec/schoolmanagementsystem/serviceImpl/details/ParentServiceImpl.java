package com.aristiec.schoolmanagementsystem.serviceImpl.details;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.ParentDetailsDto;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.CourseExamMarksDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.ExamScheduleDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectMarksDTO;
import com.aristiec.schoolmanagementsystem.dto.parentDashboard.SubjectWiseComparisonDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamSchedule;
import com.aristiec.schoolmanagementsystem.modal.exam.Marks;
import com.aristiec.schoolmanagementsystem.repository.details.ParentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.ExamScheduleRepository;
import com.aristiec.schoolmanagementsystem.repository.exam.MarksRepository;
import com.aristiec.schoolmanagementsystem.service.details.ParentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentDetailsRepository parentDetailsRepository;

     @Autowired
    private StudentDetailsRepository studentDetailsRepository;
    
    @Autowired
    private MarksRepository marksRepository;

    
    @Autowired
    private ExamScheduleRepository examScheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ParentDetailsDto getParentDetailsByStudentId(Long studentId) {

        ParentDetails parentDetails = parentDetailsRepository.findByStudentId(studentId);
        if (parentDetails == null) {
            throw new ResourceNotFoundException("Parent details not found for student ID: " + studentId);
        }

        ParentDetailsDto parentDetailsDto = modelMapper.map(parentDetails, ParentDetailsDto.class);

        if (parentDetailsDto == null) {
            throw new ResourceNotFoundException("Parent details not found for student ID: " + studentId);
        }
        return parentDetailsDto;
    }

    @Override
    public ParentDetailsDto getParentDetailsByParentId(Long parentId) {
        ParentDetails parentDetails = parentDetailsRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent details not found for parent ID: " + parentId));
        return modelMapper.map(parentDetails, ParentDetailsDto.class);
    }

    @Override
    public void deleteParentDetailsById(Long parentId) {
        if (!parentDetailsRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Parent details not found for parent ID: " + parentId);
        }
        parentDetailsRepository.deleteById(parentId);
    }

    @Override
    public ParentDetailsDto getParentDetailsById(Long parentId) {
        ParentDetails parentDetails = parentDetailsRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent details not found for parent ID: " + parentId));
        return modelMapper.map(parentDetails, ParentDetailsDto.class);
    }

    @Override
    public ParentDetailsDto getParentDetailsByEmail(String email) {
          ParentDetails parentDetails = parentDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Parent details not found for email: " +email));
        return modelMapper.map(parentDetails, ParentDetailsDto.class);

    }

     @Override
    public List<ExamScheduleDTO> getExamScheduleForParent(Long parentId) {
       List<ExamScheduleDTO> dtos = new ArrayList<>();

        ParentDetails parent = parentDetailsRepository.findById(parentId).orElse(null);
        if (parent == null || parent.getStudentDetails() == null) {
            return dtos; 
        }

        StudentDetails student = parent.getStudentDetails();
        if (student.getCourse() == null) {
            return dtos; 
        }

        List<ExamSchedule> schedules = examScheduleRepository.findByCourse(student.getCourse());

        for (ExamSchedule schedule : schedules) {
            ExamScheduleDTO dto = new ExamScheduleDTO();
            dto.setExamScheduleId(schedule.getExamScheduleId());
            dto.setStartDate(schedule.getStartDate());
            dto.setDescription(schedule.getDescription());
            dto.setDateTimeList(schedule.getDateTimeList());
            dtos.add(dto);
        }

        return dtos;
    

    }



   


    
}
