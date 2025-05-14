package com.bsuir.newPortalBack.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

  @Value("${jwt.secret}") // Injects JWT secret key from application properties
  private String secret;

  @Value("${jwt.expiration}") // Injects token expiration time (in seconds) from properties
  private Long expiration;

  // Generates a secure signing key from the secret string using HMAC-SHA algorithm
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  // Generates a JWT token for authenticated user
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userDetails.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toSet()));
    return createToken(claims, userDetails.getUsername());
  }

  // Creates a signed JWT token with specified claims and subject
  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
      .claims(claims) // Set custom claims (if any)
      .subject(subject) // Set subject (typically username)
      .issuedAt(new Date(System.currentTimeMillis())) // Set token creation time
      .expiration(new Date(System.currentTimeMillis() + expiration * 1000)) // Set expiration (converted to ms)
      .signWith(getSigningKey()) // Sign with HMAC-SHA key
      .compact(); // Build and serialize to compact string
  }

  // Validates if the token matches the user and is not expired
  public Boolean validateToken(String token) {
    try {
      Claims claims = extractAllClaims(token); // Проверяет подпись и срок
      return !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }

  // Extracts username (subject) from the token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject); // Uses Claims::getSubject method reference
  }

  // Extracts expiration date from the token
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration); // Uses Claims::getExpiration method reference
  }

  // Generic method to extract a specific claim from the token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token); // Extract all claims first
    return claimsResolver.apply(claims); // Apply resolver function to get specific claim
  }

  // Parses the token and extracts all claims (payload)
  public Claims extractAllClaims(String token) {
    return Jwts.parser()
      .verifyWith(getSigningKey()) // Set key for signature verification
      .build() // Build the parser
      .parseSignedClaims(token) // Parse and validate signed token
      .getPayload(); // Return the claims (payload) body
  }

  // Checks if the token is expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = extractExpiration(token);
    return expiration.before(new Date());
  }
}