package com.microservices.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.moviecatalogservice.models.CatalogItem;
import com.microservices.moviecatalogservice.models.Movie;
import com.microservices.moviecatalogservice.models.Rating;
import com.microservices.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

		@Autowired
		private RestTemplate restTemplate;
		
		@Autowired
		private WebClient.Builder webClientBuilder;
	
		@RequestMapping("/{userId}")
		public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
			
			//WebClient.Builder builder = WebClient.builder();
			
			//RestTemplate restTemplate = new RestTemplate();
			
			
			//get all rated movie Ids
			
			/*
			Rating rating1 = new Rating("1234",4);
			Rating rating2 = new Rating("5678",3);
			
			List<Rating> ratings = new ArrayList<Rating>();
			ratings.add(rating1);
			ratings.add(rating2);
			*/
			
			//hard coding of urls
			//UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);
			
			//no hard coding of urls using eruka load balancer 
			UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
			
			return ratings.getRatings().stream().map(rating -> {
				//for each movie id, call movie info service abd the details
				//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo" + rating.getMovieId(), Movie.class);
				Movie movie = restTemplate.getForObject("http://movie-info-service/movies/foo" + rating.getMovieId(), Movie.class);
				
				/*
				//
				Movie movie = webClientBuilder.build()
						.get()
						.uri("http://localhost:8082/movies/foo")
						.retrieve()
						.bodyToMono(Movie.class)
						.block();
				*/
				
				//put them all together
				return new CatalogItem(movie.getName(), "Desc",  rating.getRating());
				})
					.collect(Collectors.toList());
			
			//return Collections.singletonList(new CatalogItem("KGF", "Test",  4));
		}
}
