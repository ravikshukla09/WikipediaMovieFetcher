package com.thinkanalytics.rks.wiki.converters;

import com.thinkanalytics.rks.wiki.dto.MoviesList;
import com.thinkanalytics.rks.wiki.entity.Movie;

import java.util.List;
import java.util.function.Function;

public class MovieEntityListToMoviesListConverter
        implements Function<List<Movie>, MoviesList> {
    @Override
    public MoviesList apply(List<Movie> movies) {
        MoviesList moviesList = new MoviesList();
        for (Movie movie: movies) {
            MoviesList.Movie tempMovie = new MoviesList.Movie();
            tempMovie.setTitle(movie.getTitle());
            tempMovie.setReleaseDate(movie.getReleaseDate());
            tempMovie.setCategoryCount(movie.getCategories().size());

            moviesList.getMovies().add(tempMovie);
        }
        return moviesList;
    }
}
