package com.aristiec.schoolmanagementsystem.modal.library;

import com.aristiec.schoolmanagementsystem.constant.enums.BookConditionEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "book_copies")
@RequiredArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BookConditionEnum bookCondition;
    
 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

      @Column(name = "copy_count") 
    private Integer count; 

}
