package com.auth.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.entities.UserCredential;
import com.auth.repository.UserCredentialRepository;

@Service
public class UserCredentialService {
	
	@Autowired
	private UserCredentialRepository repository;

	public void saveUser(UserCredential user) {
		user.setUserId(UUID.randomUUID().toString());
		repository.save(user);
	}
	
	public UserCredential getUserByUsername(String username) {
		UserCredential user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
		
		return user;
	}
}
