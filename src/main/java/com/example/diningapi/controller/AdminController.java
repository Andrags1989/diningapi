package com.example.diningapi.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.diningapi.model.AdminReviewAction;
import com.example.diningapi.model.Restaurant;
import com.example.diningapi.model.Review;
import com.example.diningapi.model.ReviewStatus;
import com.example.diningapi.repository.RestaurantRepository;
import com.example.diningapi.repository.ReviewRepository;
import com.example.diningapi.repository.UserRepository;

public class AdminController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public AdminController(ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
    
    @GetMapping("/Id")
    public List<Review> getReviewsByStatus(@RequestParam String review_status){
    	for(Review r: reviewRepository.findAll()) {
    		if(review_status.toLowerCase().equals("pending")) {
    			return reviewRepository.findReviewByStatus(ReviewStatus.PENDING);
    		}else if(review_status.toLowerCase().equals("rejected")){
    			return reviewRepository.findReviewByStatus(ReviewStatus.REJECTED);
    		}else if(review_status.toLowerCase().equals("approved")){
    			return reviewRepository.findReviewByStatus(ReviewStatus.APPROVED);
    		}
    	}
    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/{reviewId}")
    public void performReviewAction(@PathVariable Long reviewId, @RequestBody AdminReviewAction adminReviewAction) {
    	Optional<Review> tempReview = reviewRepository.findById(reviewId.intValue());
    	if(tempReview.isPresent()) {
    		if(adminReviewAction.getAccept()) {
    			Review newReview = tempReview.get();
    			newReview.setReview("ReviewStatus.APPROVED");
        		reviewRepository.save(newReview);
    		}else if(!adminReviewAction.getAccept()) {
    			Review newReview = tempReview.get();
    			newReview.setReview("ReviewStatus.REJECTED");
        		reviewRepository.save(newReview);
    		}
    	}
    }
    
    private void updateRestaurantReviewScores(Restaurant restaurant) {
    	return;
    }
}
