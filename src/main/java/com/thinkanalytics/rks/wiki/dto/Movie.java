package com.thinkanalytics.rks.wiki.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.thinkanalytics.rks.wiki.deserializers.MovieDeserializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonDeserialize(using = MovieDeserializer.class)
public class Movie {
    private String title;
    private String intro;

    @JacksonXmlElementWrapper(localName = "actors")
    @JacksonXmlProperty(localName = "actor")
    private List<String> actors = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "directors")
    @JacksonXmlProperty(localName = "director")
    private List<String> directors = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "producers")
    @JacksonXmlProperty(localName = "producer")
    private List<String> producers = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "musicComposers")
    @JacksonXmlProperty(localName = "musicComposer")
    private List<String> musicComposers = new ArrayList<>();

    @JacksonXmlElementWrapper(localName = "productionCompanies")
    @JacksonXmlProperty(localName = "productionHouse")
    private List<String> productionCompanies = new ArrayList<>();

    private String releaseDate;
    private String duration;
    private String language;
    private String budget;
    private String boxOfficeCollection;

    @JacksonXmlElementWrapper(localName = "categories")
    @JacksonXmlProperty(localName = "category")
    private List<String> categories = new ArrayList<>();

    public void addActor(String actor) {
        actors.add(actor);
    }

    public void addDirector(String director) {
        directors.add(director);
    }

    public void addProducer(String producer) {
        producers.add(producer);
    }

    public void addMusicComposer(String musicComposer) {
        musicComposers.add(musicComposer);
    }

    public void addProductionCompany(String productionCompany) {
        productionCompanies.add(productionCompany);
    }

    public void addCategory(String category) {
        categories.add(category);
    }
}
