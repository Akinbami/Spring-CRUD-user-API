package com.dayo.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dayo.app.entity.User;
import com.dayo.app.exception.ResourceNotFoundException;
import com.dayo.app.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository UserRepository;
	
	//	get all user
	@GetMapping
	public List<User> getAllUsers(){
		return this.UserRepository.findAll();
	}
	
	//	get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value="id") long userId) {
		return this.UserRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+userId));
	}
	
	//	create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.UserRepository.save(user);
	}
	
	//	update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
		User existingUser = this.UserRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+userId));
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		
		return this.UserRepository.save(existingUser);
	}
	
	//	delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable(value="id") long userId){
		User existingUser = this.UserRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+userId));
		
		this.UserRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
	
	

}
