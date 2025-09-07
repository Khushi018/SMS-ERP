package com.aristiec.schoolmanagementsystem.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

// import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
// import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "tDRMsh8kaKFtgZI7ORPGSlcPIhAF/nuY3rOSLNARTtA="; // Use a strong key

    // private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
     private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

     public String generateToken(String username , List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
         claims.put("roles", roles);  // add roles to claims
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setHeaderParam("typ", "JWT")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) 
            .signWith(getSigningKey())
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Add method to extract roles
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, String username) {
        // return (extractUsername(token).equals(username) && !extractExpiration(token).before(new Date()));
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT Token has expired");
        } catch (Exception e) {
            throw new JwtException("Invalid JWT Token");
        }
    }
}
