package com.aristiec.schoolmanagementsystem.service.contact;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.contact.ContactDTO;

public interface ContactService {
    ContactDTO addContact(ContactDTO dto);
    ContactDTO updateContact(Long id, ContactDTO dto);
    void deleteContact(Long id);
    List<ContactDTO> getAllContacts();
    List<ContactDTO> getContactsByType(String type);
    List<ContactDTO> getAllEmergencyContacts();

}
