package com.aristiec.schoolmanagementsystem.controller.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.contact.ContactDTO;
import com.aristiec.schoolmanagementsystem.service.contact.ContactService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/contacts")
@SecurityRequirement(name = "bearerAuth") 
@Tag(name = "Contact Management", description = "Manage contacts for students, faculty, and emergency contacts")
public class ContactController {
     @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO dto) {
        return ResponseEntity.ok(contactService.addContact(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(@PathVariable Long id, @RequestBody ContactDTO dto) {
        return ResponseEntity.ok(contactService.updateContact(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok("Contact deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAll() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ContactDTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(contactService.getContactsByType(type));
    }

    @GetMapping("/emergency")
public ResponseEntity<List<ContactDTO>> getAllEmergencyContacts() {
    return ResponseEntity.ok(contactService.getAllEmergencyContacts());
}

}
