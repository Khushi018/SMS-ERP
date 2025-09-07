package com.aristiec.schoolmanagementsystem.dto.library;

import com.aristiec.schoolmanagementsystem.constant.enums.BookConditionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyResponseDTO {
    private Long copyId;

    private BookConditionEnum bookCondition;
    private Integer count;
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String shelfLocation;
    private String edition;
    private String description;
    private String imageUrl;
    private String departmentName;
}
