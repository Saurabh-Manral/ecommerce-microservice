package com.auth.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entities.UserCredential;
import com.auth.helper.UserRequest;
import com.auth.services.JwtService;
import com.auth.services.UserCredentialService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserCredentialService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserCredential userCredential) {
		userCredential.setPass(passwordEncoder.encode(userCredential.getPass()));
		userService.saveUser(userCredential);
		return ResponseEntity.status(HttpStatus.CREATED).body("Successfully register user");
	}
	
	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
		
		if(jwtService.isTokenValid(token)) {
			return ResponseEntity.status(HttpStatus.OK).body("token is valid");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("token is invalid");
		}
	}
	
	
	@PostMapping("/token")
	public ResponseEntity<String> getToken(@RequestBody UserRequest userRequest) {
		final String email = userRequest.getUsername();
		final String password = userRequest.getPassword();
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		
		if(authentication.isAuthenticated()) {
			UserCredential currentUser = userService.getUserByUsername(email);
			
			return ResponseEntity.status(HttpStatus.FOUND).body("Your token is: " + jwtService.generateToken(Map.of("id", "" + currentUser.getUserId()) ,email));
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
	}
	
	
}
