package com.thinkanalytics.rks.wiki.service;

import com.thinkanalytics.rks.wiki.entity.Movie;
import com.thinkanalytics.rks.wiki.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {
    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService = new MovieService();

    @Test
    public void testFetchMovieDetailsWhenMovieTitleNotPresentThenReturn404() {
        Mockito.when(movieRepository.findByTitleIgnoreCase("abc"))
                .thenReturn(Optional.empty());
        assertEquals(ResponseEntity.notFound().build(),
                movieService.fetchMovieDetails("abc"));
    }

    @Test
    public void testFetchMovieDetailsWhenMovieTitlePresentThenReturnOk() {
        Mockito.when(movieRepository.findByTitleIgnoreCase("abc"))
                .thenReturn(Optional.of(new Movie()));
        assertEquals(ResponseEntity.ok().build().getStatusCode(),
                movieService.fetchMovieDetails("abc").getStatusCode());
    }
}