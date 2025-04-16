package com.example.diningapi.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.diningapi.model.Review;
import com.example.diningapi.model.User;
import com.example.diningapi.repository.UserRepository;

@RequestMapping("/users")
@RestController
public class UserController {
	private final UserRepository userRepository; 
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/{displayName}")
	public User getUser(@PathVariable String name) {
		validateDisplayName(name);
		Optional<User> checkOptional = userRepository.findUserByDisplayName(name);
		if(checkOptional.isPresent()) {
			User tempUser = checkOptional.get();
			return tempUser;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addUser(@RequestBody User user) {
		validateUser(user);
		userRepository.save(user);
	}
	
	public void validateUser(User user) {
		Optional<User> tempOptional = Optional.of(user);
		try{
			User tempUser = tempOptional.get();
			tempUser.equals(userRepository.findUserByDisplayName(tempUser.getDisplayName()));
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	
	public void validateDisplayName(String displayName) {
		Optional<User> validateDisplayName = userRepository.findUserByDisplayName(displayName);
		if(validateDisplayName.isPresent()) {
			return;
		}else {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		

	}
}
