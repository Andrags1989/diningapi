package com.example.diningapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.diningapi.model.Restaurant;
import com.example.diningapi.model.Review;
import com.example.diningapi.repository.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
    private final RestaurantRepository restaurantRepository;
    private final Pattern zipCodePattern = Pattern.compile("\\d{5}");

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
		validateNewRestaurant(restaurant);
		this.restaurantRepository.save(restaurant);
		return restaurant;
	}
    
	@GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
		Integer tempId = id.intValue();
		Optional<Restaurant> tempRestaurant = this.restaurantRepository.findById(tempId);
		if(tempRestaurant.isPresent()) {
			Restaurant foundRestaurant = tempRestaurant.get();
			return foundRestaurant; 
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
    
	@GetMapping("")
    public Iterable<Restaurant> getAllRestaurants(){
		return this.restaurantRepository.findAll();
	}
    
    @GetMapping("/search")
    public Iterable<Restaurant> searchRestaurants(@RequestParam String zipcode, @RequestParam String allergy){
    	Pattern egg = Pattern.compile("(?i).*EGG.*");
    	Pattern dairy = Pattern.compile("(?i).*DAIRY.*");
    	Pattern peanut = Pattern.compile("(?i).*PEANUT.*");
    	validateZipCode(zipcode);
    	List<Restaurant> availableRestaurants = null;
    	
    	if(egg.matcher(allergy).matches()) {
    		availableRestaurants = this.restaurantRepository.findRestaurantsByZipCodeAndEggScoreNotNullOrderByEggScore(zipcode);
    	}else if(dairy.matcher(allergy).matches()) {
    		availableRestaurants = this.restaurantRepository.findRestaurantsByZipCodeAndDairyScoreNotNullOrderByDairyScore(zipcode);
    	}else if(peanut.matcher(allergy).matches()) {
    		availableRestaurants = this.restaurantRepository.findRestaurantsByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(zipcode);
    	}else {
    		return null;
    	}
    	return availableRestaurants;
    	
    }
    
    private void validateNewRestaurant(Restaurant restaurant) {
		Optional<Restaurant> tempExisting = restaurantRepository.findById(restaurant.getId().intValue());
		if(tempExisting.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}
    
    private void validateZipCode(String zipcode) {
		Matcher matcher = zipCodePattern.matcher(zipcode);
		if(matcher.matches()) {
			return;
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
    
}
