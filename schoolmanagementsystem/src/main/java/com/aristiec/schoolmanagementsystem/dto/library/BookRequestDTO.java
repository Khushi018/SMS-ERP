package com.aristiec.schoolmanagementsystem.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String shelfLocation;
    private String edition;
    private String description;
    private String imageUrl;
    private Long departmentId;
}
