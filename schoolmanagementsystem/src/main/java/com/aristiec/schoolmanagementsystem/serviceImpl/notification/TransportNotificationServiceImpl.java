package com.aristiec.schoolmanagementsystem.serviceImpl.notification;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.dto.notification.NotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.TransportNotificationRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.TransportNotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.modal.notification.TransportNotification;
import com.aristiec.schoolmanagementsystem.repository.notification.TransportNotificationRepository;
import com.aristiec.schoolmanagementsystem.service.notification.TransportNotificationService;

@Service
public class TransportNotificationServiceImpl implements TransportNotificationService {
         @Autowired
      private  TransportNotificationRepository notificationRepository;

      @Autowired
      private ModelMapper modelMapper;

    @Override
    public TransportNotificationResponseDTO createNotification(TransportNotificationRequestDTO dto) {
    TransportNotification notification = new TransportNotification();
    notification.setTitle(dto.getTitle());
    notification.setDescription(dto.getDescription());
    notification.setRouteId(dto.getRouteId());
    notification.setVehicleId(dto.getVehicleId());

    notificationRepository.save(notification);

    return modelMapper.map(notification, TransportNotificationResponseDTO.class);
    }

    @Override
    public List<TransportNotificationResponseDTO> getAllNotifications() {
           return notificationRepository.findAll()
                .stream()
                .map(n -> modelMapper.map(n, TransportNotificationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TransportNotificationResponseDTO getNotificationById(Long id) {
               TransportNotification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return modelMapper.map(notification, TransportNotificationResponseDTO.class);
    }

    @Override
    public void deleteNotification(Long id) {
         notificationRepository.deleteById(id);
    }

    @Override
    public List<NotificationResponseDTO> getByRouteAndVehicle(Long routeId, Long vehicleId) {
     return notificationRepository.findByRouteIdAndVehicleId(routeId, vehicleId).stream()
                .map(n -> new NotificationResponseDTO(n.getTitle(), n.getDescription(), n.getCreatedAt()))
                .collect(Collectors.toList());

    }
    
}
