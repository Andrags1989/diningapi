package com.example.diningapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.diningapi.model.Restaurant;
import com.example.diningapi.model.ReviewStatus;


public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{
	Optional<Restaurant> findRestaurantByNameAndZipCode(String name, String zipCode);
    List<Restaurant> findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(String zipcode);
    List<Restaurant> findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScore(String zipcode);
    List<Restaurant> findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScore(String zipcode);
	
	
	
	
}
