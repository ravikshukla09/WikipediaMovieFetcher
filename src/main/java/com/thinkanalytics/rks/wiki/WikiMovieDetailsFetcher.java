package com.thinkanalytics.rks.wiki;

import com.thinkanalytics.rks.wiki.converters.MovieDtoToEntityConverter;
import com.thinkanalytics.rks.wiki.dto.Movie;
import com.thinkanalytics.rks.wiki.entity.Category;
import com.thinkanalytics.rks.wiki.entity.ProductionHouse;
import com.thinkanalytics.rks.wiki.repository.CategoryRepository;
import com.thinkanalytics.rks.wiki.repository.MovieRepository;
import com.thinkanalytics.rks.wiki.repository.ProductionHouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class WikiMovieDetailsFetcher implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${wiki.movieDetails.url}")
    private String movieWikiPageUrl;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductionHouseRepository productionHouseRepository;

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream =
                WikiMovieDetailsFetcher.class.getResourceAsStream("/movies.txt");
        List<String> movieNames = readFromInputStream(inputStream);
        List<Movie> movies =
                Collections.synchronizedList(new ArrayList<>(movieNames.size()));

        movieNames.parallelStream().forEach(movieName -> {
            try {
                logger.debug("Processing: {}", movieName);
                Movie movie = restTemplate.getForObject(movieWikiPageUrl, Movie.class, movieName);
                movies.add(movie);
            } catch (Exception e) {
                logger.error("ERROR deserializing: {}", movieName);
            }
        });

        movies.forEach(this::saveMovieDetailsInDb);
        logger.debug("Done processing, go ahead test the APIs :)");
    }

    private void saveMovieDetailsInDb(Movie movie) {
        com.thinkanalytics.rks.wiki.entity.Movie movieEntity =
                new MovieDtoToEntityConverter().apply(movie);

        try {
            saveMovie(movieEntity);
        } catch (Exception e) {
            logger.error("ERROR in saving: {}", movie.getTitle());
            e.printStackTrace();
        }
    }

    private void saveMovie(com.thinkanalytics.rks.wiki.entity.Movie movie) {
        Set<ProductionHouse> productionHouseSet = new HashSet<>();
        for (ProductionHouse productionHouse: movie.getProductionCompanies()) {
            Optional<ProductionHouse> optionalProductionHouse =
                    productionHouseRepository.findByName(productionHouse.getName());
            if (optionalProductionHouse.isPresent()) {
                productionHouseSet.add(optionalProductionHouse.get());
            } else {
                productionHouseRepository.save(productionHouse);
                productionHouseSet.add(productionHouse);
            }
        }
        movie.getProductionCompanies().clear();
        movie.getProductionCompanies().addAll(productionHouseSet);

        Set<Category> categorySet = new HashSet<>();
        for (Category category: movie.getCategories()) {
            Optional<Category> optionalCategory =
                    categoryRepository.findByName(category.getName());
            if (optionalCategory.isPresent()) {
                categorySet.add(optionalCategory.get());
            } else {
                categoryRepository.save(category);
                categorySet.add(category);
            }
        }
        movie.getCategories().clear();
        movie.getCategories().addAll(categorySet);

        movieRepository.save(movie);
    }

    private List<String> readFromInputStream(InputStream inputStream) throws IOException {
        List<String> movies = new ArrayList<>();

        try (BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                movies.add(line.trim());
            }
        }
        return movies;
    }
}
