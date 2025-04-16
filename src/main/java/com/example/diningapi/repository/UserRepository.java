package com.example.diningapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.diningapi.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	Optional<User> findUserByDisplayName(String displayName);

}
