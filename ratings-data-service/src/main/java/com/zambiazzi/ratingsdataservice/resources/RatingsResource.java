package com.zambiazzi.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zambiazzi.ratingsdataservice.models.Rating;
import com.zambiazzi.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}

	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> userRatings = Arrays.asList(new Rating("100", 4), new Rating("200", 6));
		UserRating userRating = new UserRating();
		userRating.setRatings(userRatings);
		return userRating;
	}
}
