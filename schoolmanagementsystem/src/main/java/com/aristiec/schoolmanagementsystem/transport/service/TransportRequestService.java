package com.aristiec.schoolmanagementsystem.transport.service;

import com.aristiec.schoolmanagementsystem.constant.enums.RequestStatusEnum;
import com.aristiec.schoolmanagementsystem.transport.dto.TransportRequestCreateDto;
import com.aristiec.schoolmanagementsystem.transport.dto.TransportRequestReadDto;

import java.util.List;
import java.util.Optional;

public interface TransportRequestService {
    TransportRequestReadDto createTransportRequest(TransportRequestCreateDto createDto);

    List<TransportRequestReadDto> getAllTransportRequests();

    Optional<TransportRequestReadDto> getTransportRequestById(Long id);

    TransportRequestReadDto updateTransportRequest(Long id, TransportRequestCreateDto updateDto);

    boolean deleteTransportRequest(Long id);

    TransportRequestReadDto updateRequestStatus(Long id, RequestStatusEnum status);
}
