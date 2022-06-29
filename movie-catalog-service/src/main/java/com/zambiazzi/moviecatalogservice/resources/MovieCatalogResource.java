package com.zambiazzi.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zambiazzi.moviecatalogservice.models.CatalogItem;
import com.zambiazzi.moviecatalogservice.models.Movie;
import com.zambiazzi.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {

		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userId,
				UserRating.class);

		return ratings.getRatings().stream().map(rating -> {

			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
					Movie.class);

//			Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId())
//					.retrieve().bodyToMono(Movie.class).block();

			return new CatalogItem(movie.getName(), "movie.desc", rating.getRating());
		}).collect(Collectors.toList());
	}
}
