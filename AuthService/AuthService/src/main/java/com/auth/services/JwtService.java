package com.auth.services;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
	
	@Autowired
	private UserCredentialService userService;
	
	//extracting all claims or data
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	//making signIn key
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	//extract single claim
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	//extracting username from token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//generate token
	public String generateToken(Map<String, Object> extraClaims, String username) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS512)
				.compact();
	}
	
	//generate token with only username
	public String generateToken(String username) {
		return generateToken(new HashMap<>(), username);
	}
	
	//check weather token is valid or not
	public boolean isTokenValid(String token) {
		String tokenUsername = extractUsername(token);
		
		return (tokenUsername.equals(userService.getUserByUsername(tokenUsername).getUsername()) && !isTokenExpired(token));
		
	}
	
	//check weather token is expired or not
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	//extracting the expiration date of token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
