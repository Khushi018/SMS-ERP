package com.aristiec.schoolmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LibraryCardDTO {
    private String cardNumber;
    private Long studentId;  // internal DB ID or you can use studentCode
}
