package com.aristiec.schoolmanagementsystem.repository.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.library.Wishlist;


@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    
    List<Wishlist> findByStudentId(Long studentId);

    boolean existsByStudentIdAndBookBookId(Long studentId, Long bookId);

    void deleteByStudentIdAndBookBookId(Long studentId, Long bookId);
}
