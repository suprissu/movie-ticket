package com.adpro.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Autowired
	public MovieProxy getMovieProxy(MovieRepository movieRepository) throws Exception {
		return new MovieProxy(movieRepository);
	}

}
