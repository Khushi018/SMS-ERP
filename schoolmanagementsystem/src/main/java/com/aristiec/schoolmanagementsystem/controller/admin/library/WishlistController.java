package com.aristiec.schoolmanagementsystem.controller.admin.library;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.library.BookResponseDTO;
import com.aristiec.schoolmanagementsystem.service.library.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/library/wishlist")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Wishlist Management", description = "Manage student book wishlists")
public class WishlistController {
    
      @Autowired
    private WishlistService wishlistService;


     @PostMapping("/{studentId}/book/{bookId}")
    public ResponseEntity<Void>addToWishlist(@PathVariable Long studentId, @PathVariable Long bookId){
         
        wishlistService.addToWishlist(studentId, bookId);
        return ResponseEntity.ok().build();

    }

     @DeleteMapping("/{studentId}/book/{bookId}")
     @Transactional
    public ResponseEntity<Void>removeFromWishlist(@PathVariable Long studentId, @PathVariable Long bookId){
         
        wishlistService.removeFromWishlist(studentId, bookId);
        return ResponseEntity.ok().build();

    }

     
    @GetMapping("/{studentId}")
    public ResponseEntity<List<BookResponseDTO>> getStudentWishlist(@PathVariable Long studentId){

          return ResponseEntity.ok(wishlistService.getWishlistBooks(studentId));
    }






}
