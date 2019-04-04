package com.adpro.movie;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Movie findMovieByName(String name);
    List<Movie> findMoviesByReleaseDateAfter(LocalDate date);
}
