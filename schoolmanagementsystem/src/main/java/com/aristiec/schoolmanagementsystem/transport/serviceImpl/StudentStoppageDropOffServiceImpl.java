package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import com.aristiec.schoolmanagementsystem.transport.dto.StudentStoppageDropOffDto;
import com.aristiec.schoolmanagementsystem.transport.modal.Stoppage;
import com.aristiec.schoolmanagementsystem.transport.modal.StudentStoppageDropOff;
import com.aristiec.schoolmanagementsystem.transport.modal.StudentTransportAssignment;
import com.aristiec.schoolmanagementsystem.transport.repository.StoppageRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StudentStoppageDropOffRepository;
import com.aristiec.schoolmanagementsystem.transport.repository.StudentTransportAssignmentRepository;
import com.aristiec.schoolmanagementsystem.transport.service.StudentStoppageDropOffService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
public class StudentStoppageDropOffServiceImpl implements StudentStoppageDropOffService {

    private final StudentStoppageDropOffRepository dropOffRepo;
    private final StudentTransportAssignmentRepository assignmentRepo;
    private final StoppageRepository stoppageRepo;
    private final ModelMapper mapper;

    public StudentStoppageDropOffServiceImpl(
            StudentStoppageDropOffRepository dropOffRepo,
            StudentTransportAssignmentRepository assignmentRepo,
            StoppageRepository stoppageRepo,
            ModelMapper mapper) {
        this.dropOffRepo = dropOffRepo;
        this.assignmentRepo = assignmentRepo;
        this.stoppageRepo = stoppageRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public StudentStoppageDropOffDto assignDropOff(Long assignmentId, StudentStoppageDropOffDto dto) {
        StudentTransportAssignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Stoppage stoppage = stoppageRepo.findById(dto.getStoppageId())
                .orElseThrow(() -> new RuntimeException("Stoppage not found"));

        if (dto.getDropOff()) {
            dropOffRepo.deleteByAssignment_Id(assignmentId); // clear any old drop-off
            StudentStoppageDropOff dropOffEntity = new StudentStoppageDropOff();
            dropOffEntity.setAssignment(assignment);
            dropOffEntity.setStoppage(stoppage);
            dropOffEntity.setDropOff(true);
            dropOffRepo.save(dropOffEntity);
        } else {
            dropOffRepo.deleteByAssignment_IdAndStoppage_Id(assignmentId, dto.getStoppageId());
        }

        return dto;
    }

    @Override
    public List<StudentStoppageDropOffDto> getDropOffsByAssignmentId(Long assignmentId) {
        return dropOffRepo.findByAssignment_Id(assignmentId).stream()
                .map(e -> {
                    StudentStoppageDropOffDto dto = new StudentStoppageDropOffDto();
                    dto.setStoppageId(e.getStoppage().getId());
                    dto.setDropOff(e.getDropOff());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
