package com.aristiec.schoolmanagementsystem.transport.serviceImpl;

import com.aristiec.schoolmanagementsystem.constant.enums.DayOfWeek;
import com.aristiec.schoolmanagementsystem.constant.enums.RequestStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.RequestTypeEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.repository.details.CourseRepository;
import com.aristiec.schoolmanagementsystem.repository.details.StudentDetailsRepository;
import com.aristiec.schoolmanagementsystem.transport.dto.TransportRequestCreateDto;
import com.aristiec.schoolmanagementsystem.transport.dto.TransportRequestReadDto;
import com.aristiec.schoolmanagementsystem.transport.modal.TransportRequest;
import com.aristiec.schoolmanagementsystem.transport.repository.TransportRequestRepository;
import com.aristiec.schoolmanagementsystem.transport.service.TransportRequestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransportRequestServiceImpl implements TransportRequestService {

    @Autowired
    private TransportRequestRepository transportRequestRepository;

    @Autowired
    private StudentDetailsRepository studentRepository;

    @Transactional
    public TransportRequestReadDto createTransportRequest(TransportRequestCreateDto createDto) {
        TransportRequest transportRequest = mapToEntity(createDto);
        TransportRequest savedTransportRequest = transportRequestRepository.save(transportRequest);

        return mapToReadDto(savedTransportRequest);
    }

    public List<TransportRequestReadDto> getAllTransportRequests() {
        List<TransportRequest> transportRequests = transportRequestRepository.findAll();
        return transportRequests.stream()
                .map(this::mapToReadDto)
                .collect(Collectors.toList());
    }

    public Optional<TransportRequestReadDto> getTransportRequestById(Long id) {
        Optional<TransportRequest> transportRequest = transportRequestRepository.findById(id);
        return transportRequest.map(this::mapToReadDto);
    }

    @Transactional
    public TransportRequestReadDto updateTransportRequest(Long id, TransportRequestCreateDto updateDto) {
        Optional<TransportRequest> existingTransportRequest = transportRequestRepository.findById(id);
        if (existingTransportRequest.isPresent()) {
            TransportRequest transportRequest = existingTransportRequest.get();
            transportRequest = mapToEntity(updateDto);
            transportRequest.setId(id); // ensure the existing ID is set
            TransportRequest updatedTransportRequest = transportRequestRepository.save(transportRequest);
            return mapToReadDto(updatedTransportRequest);
        }
        return null; // Or throw a custom exception
    }

    @Transactional
    public boolean deleteTransportRequest(Long id) {
        if (transportRequestRepository.existsById(id)) {
            transportRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TransportRequestReadDto updateRequestStatus(Long requestId, RequestStatusEnum newStatus) {
        Optional<TransportRequest> optionalRequest = transportRequestRepository.findById(requestId);
        if (optionalRequest.isEmpty()) {
            throw new IllegalArgumentException("Transport Request with ID " + requestId + " not found.");
        }

        TransportRequest request = optionalRequest.get();
        request.setRequestStatus(newStatus);
        TransportRequest savedTransportRequest = transportRequestRepository.save(request);

        return mapToReadDto(savedTransportRequest);
    }


    private TransportRequest mapToEntity(TransportRequestCreateDto createDto) {
        TransportRequest transportRequest = new TransportRequest();
        transportRequest.setName(createDto.getName());

        transportRequest.setNumber(createDto.getNumber());
        transportRequest.setEmail(createDto.getEmail());
        transportRequest.setEmergencyNo1(createDto.getEmergencyNo1());
        transportRequest.setEmergencyNo2(createDto.getEmergencyNo2());
        transportRequest.setRequestType(RequestTypeEnum.valueOf(createDto.getRequestType()));
        transportRequest.setStartDate(LocalDate.parse(createDto.getStartDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        transportRequest.setLocation(createDto.getLocation());
        transportRequest.setPincode(createDto.getPincode());
        transportRequest.setLandmark(createDto.getLandmark());
        transportRequest.setRequestStatus(RequestStatusEnum.REQUEST_PENDING);
        StudentDetails student = studentRepository.findById(createDto.getStudentId()).
                orElseThrow(() -> new RuntimeException("Student not found"));

        transportRequest.setStudent(student);

        transportRequest.setDays(mapDays(createDto.getDay()));
        return transportRequest;
    }

    private DayOfWeek[] mapDays(String[] days) {
        return Arrays.stream(days)
                .map(DayOfWeek::valueOf)
                .toArray(DayOfWeek[]::new);
    }

    private String[] mapDaysToString(DayOfWeek[] days) {
        return Arrays.stream(days)
                .map(Enum::name)
                .toArray(String[]::new);
    }

    private TransportRequestReadDto mapToReadDto(TransportRequest transportRequest) {
        TransportRequestReadDto dto = new TransportRequestReadDto();
        dto.setId(transportRequest.getId());
        dto.setName(transportRequest.getName());
        dto.setStudentId(transportRequest.getStudent().getId());

        dto.setRequestStatus(transportRequest.getRequestStatus().name());
        dto.setNumber(transportRequest.getNumber());
        dto.setEmail(transportRequest.getEmail());
        dto.setRequestType(transportRequest.getRequestType().name());
        dto.setStartDate(transportRequest.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        dto.setLocation(transportRequest.getLocation());
        dto.setPincode(transportRequest.getPincode());
        dto.setLandmark(transportRequest.getLandmark());
        dto.setDay(mapDaysToString(transportRequest.getDays()));
        return dto;
    }
}
