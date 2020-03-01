package com.thinkanalytics.rks.wiki.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MoviesList {
    @Data
    public static class Movie {
       private String title;
       private String releaseDate;
       private int categoryCount;
    }

    @JacksonXmlElementWrapper(localName = "movies")
    @JacksonXmlProperty(localName = "movie")
    private List<Movie> movies = new ArrayList<>();
}
