package com.aristiec.schoolmanagementsystem.repository.contact;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.constant.enums.ContactTypeEnum;
import com.aristiec.schoolmanagementsystem.modal.contact.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByType(ContactTypeEnum type);
}
