package com.thinkanalytics.rks.wiki.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchingMovies {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Movie {
        private String title;
        private int    matchCount;
    }

    @JacksonXmlElementWrapper(localName = "matchingMovies")
    @JacksonXmlProperty(localName = "movie")
    private List<Movie> matchingMovies = new ArrayList<>();
}
