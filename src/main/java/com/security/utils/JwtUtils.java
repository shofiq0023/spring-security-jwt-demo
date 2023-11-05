package com.security.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private final String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	private final long expirationTime = 86400000;

	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public Date getExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	public Boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date(System.currentTimeMillis()));
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(String subject) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, subject);
	}
	
	public String generateToken(Map<String, Object> claims, String subject) {
		return createToken(claims, subject);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts
				.builder()
				.setSubject(subject)
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();

	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
