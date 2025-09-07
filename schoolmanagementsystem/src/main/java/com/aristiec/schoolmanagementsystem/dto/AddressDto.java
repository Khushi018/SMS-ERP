package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String type;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;

}
