package com.aristiec.schoolmanagementsystem.modal.library;

import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "books")
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String title;
    private String author;
    private String isbn;

    private String category;
    private String shelfLocation;
    private String edition;

     @Column(length = 1000)
    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookCopy> copies;

     @Transient
    private boolean isAvailable;

}
