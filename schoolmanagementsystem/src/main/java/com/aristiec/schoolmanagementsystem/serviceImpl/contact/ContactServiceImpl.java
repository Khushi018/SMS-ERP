package com.aristiec.schoolmanagementsystem.serviceImpl.contact;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.ContactTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.contact.ContactDTO;
import com.aristiec.schoolmanagementsystem.exception.ResourceNotFoundException;
import com.aristiec.schoolmanagementsystem.modal.contact.Contact;
import com.aristiec.schoolmanagementsystem.repository.contact.ContactRepository;
import com.aristiec.schoolmanagementsystem.service.contact.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

     @Autowired
    private ContactRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContactDTO addContact(ContactDTO dto) {
           Contact contact = modelMapper.map(dto, Contact.class);
        return modelMapper.map(repository.save(contact), ContactDTO.class);
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO dto) {
          Contact contact = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

        contact.setTitle(dto.getTitle());
        contact.setType(dto.getType());
        contact.setDescription(dto.getDescription());
        contact.setPhoneNumber(dto.getPhoneNumber());

        return modelMapper.map(repository.save(contact), ContactDTO.class);
    }

    @Override
    public void deleteContact(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contact not found");
        }
        repository.deleteById(id);
    }

    @Override
    public List<ContactDTO> getAllContacts() {
         return repository.findAll().stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDTO> getContactsByType(String type) {
          ContactTypeEnum contactType = ContactTypeEnum.valueOf(type.toUpperCase());
        return repository.findByType(contactType).stream()
                .map(contact -> modelMapper.map(contact, ContactDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDTO> getAllEmergencyContacts() {
          return repository.findByType(ContactTypeEnum.EMERGENCY)
            .stream()
            .map(contact -> modelMapper.map(contact, ContactDTO.class))
            .collect(Collectors.toList());
    }
    
}
