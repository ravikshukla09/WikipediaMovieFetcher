package com.thinkanalytics.rks.wiki.converters;

import com.thinkanalytics.rks.wiki.dto.Movie;
import com.thinkanalytics.rks.wiki.entity.Category;
import com.thinkanalytics.rks.wiki.entity.Person;
import com.thinkanalytics.rks.wiki.entity.ProductionHouse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class MovieDtoToEntityConverter implements
        Function<Movie, com.thinkanalytics.rks.wiki.entity.Movie> {

    @Override
    public com.thinkanalytics.rks.wiki.entity.Movie apply(Movie movie) {
        com.thinkanalytics.rks.wiki.entity.Movie movieEntity =
                new com.thinkanalytics.rks.wiki.entity.Movie();

        movieEntity.setTitle(movie.getTitle());
        movieEntity.setIntro(movie.getIntro());
        movieEntity.setReleaseDate(movie.getReleaseDate());
        movieEntity.setLanguage(movie.getLanguage());
        movieEntity.setDuration(movie.getDuration());
        movieEntity.setBudget(movie.getBudget());
        movieEntity.setBoxOfficeCollection(movie.getBoxOfficeCollection());
        movieEntity.setCategoryCount(movie.getCategories().size());

        List<Person> actors = new ArrayList<>();
        for (String actor: movie.getActors()) {
            Person person = new Person();
            person.setName(actor);
            person.setType(Person.Type.ACTOR);
            person.setMovie(movieEntity);
            actors.add(person);
        }
        movieEntity.setActors(actors);

        List<Person> producers = new ArrayList<>();
        for (String producer: movie.getProducers()) {
            Person person = new Person();
            person.setName(producer);
            person.setType(Person.Type.PRODUCER);
            person.setMovie(movieEntity);
            producers.add(person);
        }
        movieEntity.setProducers(producers);

        List<Person> directors = new ArrayList<>();
        for (String director: movie.getDirectors()) {
            Person person = new Person();
            person.setName(director);
            person.setType(Person.Type.DIRECTOR);
            person.setMovie(movieEntity);
            directors.add(person);
        }
        movieEntity.setDirectors(directors);

        List<Person> musicComposers = new ArrayList<>();
        for (String musicComposer: movie.getMusicComposers()) {
            Person person = new Person();
            person.setName(musicComposer);
            person.setType(Person.Type.MUSIC_COMPOSER);
            person.setMovie(movieEntity);
            musicComposers.add(person);
        }
        movieEntity.setMusicComposers(musicComposers);

        Set<ProductionHouse> productionCompanies = new HashSet<>();
        for (String productionCompany: movie.getProductionCompanies()) {
            ProductionHouse productionHouse = new ProductionHouse();
            productionHouse.setName(productionCompany);
            productionCompanies.add(productionHouse);
        }
        movieEntity.setProductionCompanies(productionCompanies);

        Set<Category> categories = new HashSet<>();
        for (String categoryName: movie.getCategories()) {
            Category category = new Category();
            category.setName(categoryName);
            categories.add(category);
        }
        movieEntity.setCategories(categories);

        return movieEntity;
    }
}
