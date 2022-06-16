package com.microservices.ratingdataservice.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.ratingdataservice.models.Rating;
import com.microservices.ratingdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		
		Rating rating1 = new Rating("1234",4);
		Rating rating2 = new Rating("5678",3);
		
		List<Rating> ratings = new ArrayList<Rating>();
		ratings.add(rating1);
		ratings.add(rating2);
		
		UserRating userRating = new UserRating();
		userRating.setRatings(ratings);
		return userRating;
	}
	
	
	

}
