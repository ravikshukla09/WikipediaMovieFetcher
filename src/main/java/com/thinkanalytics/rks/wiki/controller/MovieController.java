package com.thinkanalytics.rks.wiki.controller;

import com.thinkanalytics.rks.wiki.WikiMovieDetailsFetcher;
import com.thinkanalytics.rks.wiki.dto.MatchingMovies;
import com.thinkanalytics.rks.wiki.dto.Movie;
import com.thinkanalytics.rks.wiki.dto.MoviesList;
import com.thinkanalytics.rks.wiki.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    @Autowired
    private WikiMovieDetailsFetcher wikiMovieDetailsFetcher;

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/movie", produces = { "application/json", "application/xml" })
    public ResponseEntity<Movie> getMovieDetails(@RequestParam(name = "title") String movieName) {
        return movieService.fetchMovieDetails(movieName);
    }

    @GetMapping(value = "/movies", produces = { "application/json", "application/xml" })
    public MoviesList getMovies(@RequestParam(name = "sort_by", required = false) String sortBy) {
        return movieService.fetchMoviesList(sortBy);
    }

    @GetMapping(value = "/movie/match", produces = { "application/json", "application/xml" })
    public ResponseEntity<MatchingMovies> getMatchingMovies(@RequestParam String title,
                                            @RequestParam(required = false, defaultValue = "50") int limit) {
        return movieService.findMatchingMovies(title, limit);
    }
}
