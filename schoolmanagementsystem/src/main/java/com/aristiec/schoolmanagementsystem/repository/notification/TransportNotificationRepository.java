package com.aristiec.schoolmanagementsystem.repository.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.notification.TransportNotification;

@Repository
public interface TransportNotificationRepository extends JpaRepository<TransportNotification, Long> {
    List<TransportNotification> findByRouteIdAndVehicleId(Long routeId, Long vehicleId);
}
