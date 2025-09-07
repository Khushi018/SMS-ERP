package com.aristiec.schoolmanagementsystem.service.notification;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.notification.NotificationResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.TransportNotificationRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.notification.TransportNotificationResponseDTO;

public interface TransportNotificationService {
     TransportNotificationResponseDTO createNotification(TransportNotificationRequestDTO dto);
    List<TransportNotificationResponseDTO> getAllNotifications();
    TransportNotificationResponseDTO getNotificationById(Long id);
    void deleteNotification(Long id);
    List<NotificationResponseDTO> getByRouteAndVehicle(Long routeId, Long vehicleId);

}
