package com.thinkanalytics.rks.wiki.service;

import com.thinkanalytics.rks.wiki.converters.MovieEntityListToMoviesListConverter;
import com.thinkanalytics.rks.wiki.converters.MovieEntitytoDtoConverter;
import com.thinkanalytics.rks.wiki.converters.MovieToMatchingMoviesConverter;
import com.thinkanalytics.rks.wiki.dto.MatchingMovies;
import com.thinkanalytics.rks.wiki.dto.Movie;
import com.thinkanalytics.rks.wiki.dto.MoviesList;
import com.thinkanalytics.rks.wiki.entity.Category;
import com.thinkanalytics.rks.wiki.entity.Person;
import com.thinkanalytics.rks.wiki.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public ResponseEntity<Movie> fetchMovieDetails(String movieName) {
        Optional<com.thinkanalytics.rks.wiki.entity.Movie> optionalMovie =
                movieRepository.findByTitleIgnoreCase(movieName);

        return optionalMovie.map(movie -> new ResponseEntity<>
                (new MovieEntitytoDtoConverter().apply(movie), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public MoviesList fetchMoviesList(String sortBy) {
        Sort sort = getSortProperties(sortBy);
        List<com.thinkanalytics.rks.wiki.entity.Movie> movieList =
                movieRepository.findAll(sort);

        return new MovieEntityListToMoviesListConverter().apply(movieList);
    }

    private Sort getSortProperties(String sortBy) {
        List<Sort.Order> sortOrders = new ArrayList<>();

        if (sortBy == null) {
            return Sort.by(sortOrders);
        }

        String[] sortFields = sortBy.split(",");
        for (String sortField: sortFields) {
            char sign = sortField.charAt(0);
            Sort.Direction direction = Sort.Direction.ASC;
            if (sign == '-') {
                direction = Sort.Direction.DESC;
            }

            String property = sortField;
            if (sign == '-' || sign == '+') {
                property = sortField.substring(1);
            }
            String entityPropertyName = null;
            if (property.equalsIgnoreCase("title")) {
                entityPropertyName = "title";
            } else if (property.equalsIgnoreCase("releaseDate")) {
                entityPropertyName = "releaseDate";
            } else if (property.equalsIgnoreCase("categoryCount")) {
                entityPropertyName = "categoryCount";
            }

            if (entityPropertyName != null) {
                Sort.Order order = new Sort.Order(direction, entityPropertyName);
                sortOrders.add(order);
            }
        }
        return Sort.by(sortOrders);
    }

    public ResponseEntity<MatchingMovies> findMatchingMovies(String title, int limit) {
        Optional<com.thinkanalytics.rks.wiki.entity.Movie> optionalMovie =
                movieRepository.findByTitleIgnoreCase(title);

        if (!optionalMovie.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        com.thinkanalytics.rks.wiki.entity.Movie movie = optionalMovie.get();
        List<Object[]> matchingMoviesObjects = movieRepository.findMatchingMovies
                (movie.getActors().stream().map(Person::getName).collect(Collectors.toList()),
                        movie.getCategories().stream().map(Category::getId).collect(Collectors.toList()), limit);

        Map<Integer, String> movieIdNameMap = new HashMap<>();
        List<com.thinkanalytics.rks.wiki.entity.Movie> movies = movieRepository
                .findAllById(matchingMoviesObjects.stream().map(objects -> Integer.parseInt(objects[0].toString())).collect(Collectors.toList()));
        movies.forEach(movie1 -> movieIdNameMap.put(movie1.getId(), movie1.getTitle()));

        MatchingMovies matchingMovies = new MatchingMovies();
        for (Object[] object: matchingMoviesObjects) {
            MatchingMovies.Movie matchingMovie = new MatchingMovies.Movie();
            matchingMovie.setTitle(movieIdNameMap.get(Integer.parseInt(object[0].toString())));
            matchingMovie.setMatchCount(Integer.parseInt(object[1].toString()) );
            matchingMovies.getMatchingMovies().add(matchingMovie);
        }

        return new ResponseEntity<>(matchingMovies, HttpStatus.OK);
    }
}
