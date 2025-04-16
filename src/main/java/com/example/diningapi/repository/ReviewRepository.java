package com.example.diningapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.diningapi.model.Restaurant;
import com.example.diningapi.model.Review;
import com.example.diningapi.model.ReviewStatus;

public interface ReviewRepository extends CrudRepository<Review, Integer>{
	List<Review> findReviewByStatus(ReviewStatus reviewStatus);
	List<Review> findReviewByRestaurantIdAndStatus(Long restaurantId, ReviewStatus reviewStatus);
}
