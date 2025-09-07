package com.aristiec.schoolmanagementsystem.service.library;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.library.BookResponseDTO;

public interface WishlistService {
    void addToWishlist(Long studentId, Long bookId);
void removeFromWishlist(Long studentId, Long bookId);
List<BookResponseDTO> getWishlistBooks(Long studentId);
}
