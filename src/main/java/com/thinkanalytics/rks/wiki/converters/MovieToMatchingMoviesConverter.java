package com.thinkanalytics.rks.wiki.converters;

import com.thinkanalytics.rks.wiki.dto.MatchingMovies;
import com.thinkanalytics.rks.wiki.entity.Category;
import com.thinkanalytics.rks.wiki.entity.Movie;
import com.thinkanalytics.rks.wiki.entity.Person;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieToMatchingMoviesConverter {

    /*public MatchingMovies convert(List<Movie> movies, Movie originalMovie) {
       *//* Set<String> actors = originalMovie.getActors().stream()
                .map(Person::getName).collect(Collectors.toSet());
        Set<String> categories = originalMovie.getCategories().stream()
                .map(Category::getName).collect(Collectors.toSet());
        MatchingMovies matchingMovies = new MatchingMovies();

        for (Movie movie: movies) {
            MatchingMovies.Movie matchingMovie = new MatchingMovies.Movie();
            matchingMovie.setTitle(movie.);
        }
        return matchingMovies;*//*
    }*/
}
