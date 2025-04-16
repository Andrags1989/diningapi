package com.example.diningapi.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.example.diningapi.repository.ReviewRepository;
import com.example.diningapi.repository.UserRepository;

@RequestMapping("/review")
@RestController
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
	
	@GetMapping("/{id}")
	public Review getReviewById(@RequestParam Long id) {
		Optional<Review> checkOptional = reviewRepository.findById(id.intValue());
		if(checkOptional.isPresent()) {
			Review tempReview = checkOptional.get();
			return tempReview;
		}
		return null;
	}
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Review addUserReview(@RequestBody Review review) {
		validateReview(review); 
		this.reviewRepository.save(review);
		return review;
	}
	
	public void validateReview(Review review) {
		Optional<Review> validateExisting = this.reviewRepository.findById(review.getId().intValue());
		if(validateExisting.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}
	
	
}
