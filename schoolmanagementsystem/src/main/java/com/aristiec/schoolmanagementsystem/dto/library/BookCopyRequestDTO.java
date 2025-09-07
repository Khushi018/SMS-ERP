package com.aristiec.schoolmanagementsystem.dto.library;

import com.aristiec.schoolmanagementsystem.constant.enums.BookConditionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyRequestDTO {
    private Long bookId;
    private BookConditionEnum bookCondition;
    private Integer count;
}
