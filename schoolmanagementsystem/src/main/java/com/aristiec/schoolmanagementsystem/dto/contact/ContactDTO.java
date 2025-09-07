package com.aristiec.schoolmanagementsystem.dto.contact;

import com.aristiec.schoolmanagementsystem.constant.enums.ContactTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private Long id;
    private String title;
    private ContactTypeEnum type;
    private String description;
    private String phoneNumber;
}
