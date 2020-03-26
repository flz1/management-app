package com.usermanagement.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.User;
import com.usermanagement.UserRepository;
import com.usermanagement.dto.Auth;
import com.usermanagement.dto.CreateAccountDto;
import com.usermanagement.dto.UpdateUserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository _userRepository;
	
	private ArrayList<String> authenticatedEmails = new ArrayList<String>();
	
	@GetMapping()
	public List<User> onGetAll(){
		return this._userRepository.findAll();
	}
	
	@PostMapping("/auth")
	public Object onAuthentication(
			@RequestBody() Auth data
	) {
		if (this.authenticatedEmails.contains(data.email)) {
			return ResponseEntity.badRequest().body("User already authenticated");
		}
		
		Optional<User> target = this._userRepository.findByEmailAndPassword(data.email, data.password);
		if (!target.isPresent()) {
			return ResponseEntity.badRequest().body("User does not exist");
		}
		
		this.authenticatedEmails.add(data.email);
		return true;
	}
	
	@PutMapping()
	public User onSignup(@Valid @RequestBody() CreateAccountDto data) {
		User user = new User(data.name, data.email, data.password );
		return this._userRepository.save(user);
	}
	
	@DeleteMapping("/{id}")
	public Object onRemove(@PathVariable("id") Long id) {		
		try {
			 this._userRepository.deleteById(id);
			 return true;
		}
		catch(IllegalArgumentException ex ) {
			return false;
		}
		catch(EmptyResultDataAccessException ex) {
			return ResponseEntity.badRequest().body("[Error] User does not exist");
		}
	}
	
	@PostMapping()
	public Object onUpdate(@Valid @RequestBody() UpdateUserDto data ) {
		Optional<User> target = this._userRepository.findById(data.id);
		if (!target.isPresent()) {
			return ResponseEntity.badRequest().body("User does not exist");
		} 
		
		target.get().setName(data.name);
		target.get().setEmail(data.email);
		return this._userRepository.save(target.get());
	}
} 
