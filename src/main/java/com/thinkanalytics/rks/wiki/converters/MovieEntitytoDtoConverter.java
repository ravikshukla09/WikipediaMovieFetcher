package com.thinkanalytics.rks.wiki.converters;

import com.thinkanalytics.rks.wiki.entity.Category;
import com.thinkanalytics.rks.wiki.entity.Movie;
import com.thinkanalytics.rks.wiki.entity.Person;
import com.thinkanalytics.rks.wiki.entity.ProductionHouse;

import java.util.function.Function;

public class MovieEntitytoDtoConverter implements
        Function<Movie, com.thinkanalytics.rks.wiki.dto.Movie> {

    @Override
    public com.thinkanalytics.rks.wiki.dto.Movie apply(Movie movie) {
        com.thinkanalytics.rks.wiki.dto.Movie movieDto =
                new com.thinkanalytics.rks.wiki.dto.Movie();

        movieDto.setTitle(movie.getTitle());
        movieDto.setIntro(movie.getIntro());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setDuration(movie.getDuration());
        movieDto.setLanguage(movie.getLanguage());
        movieDto.setBudget(movie.getBudget());
        movieDto.setBoxOfficeCollection(movie.getBoxOfficeCollection());

        for (Person actor: movie.getActors()) {
            movieDto.addActor(actor.getName());
        }
        for (Person director: movie.getDirectors()) {
            movieDto.addDirector(director.getName());
        }
        for (Person producer: movie.getProducers()) {
            movieDto.addProducer(producer.getName());
        }
        for (Person musicComposer: movie.getMusicComposers()) {
            movieDto.addMusicComposer(musicComposer.getName());
        }
        for (ProductionHouse productionHouse: movie.getProductionCompanies()) {
            movieDto.addProductionCompany(productionHouse.getName());
        }
        for (Category category: movie.getCategories()) {
            movieDto.addCategory(category.getName());
        }

        return movieDto;
    }
}
