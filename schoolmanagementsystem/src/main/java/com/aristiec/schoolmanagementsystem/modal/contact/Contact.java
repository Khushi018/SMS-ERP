package com.aristiec.schoolmanagementsystem.modal.contact;

import com.aristiec.schoolmanagementsystem.constant.enums.ContactTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; 

    @Enumerated(EnumType.STRING)
    private ContactTypeEnum type;

    private String description; 

    private String phoneNumber;
}
