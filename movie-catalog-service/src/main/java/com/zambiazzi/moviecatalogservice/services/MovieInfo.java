package com.zambiazzi.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zambiazzi.moviecatalogservice.models.CatalogItem;
import com.zambiazzi.moviecatalogservice.models.Movie;
import com.zambiazzi.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {

//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem", threadPoolKey = "movieInfoPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"), @HystrixProperty(name = "maxQueueSize", value = "10") })
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

//		Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId())
//									.retrieve().bodyToMono(Movie.class).block();

		return new CatalogItem(movie.getName(), "movie.desc", rating.getRating());
	}

	// Fallback
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie not found", "", rating.getRating());
	}

}
