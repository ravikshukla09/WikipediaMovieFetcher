package com.thinkanalytics.rks.wiki.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(length = 3000)
    private String intro;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Where(clause = "type = 'ACTOR'")
    private List<Person> actors = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Where(clause = "type = 'DIRECTOR'")
    private List<Person> directors = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Where(clause = "type = 'PRODUCER'")
    private List<Person> producers = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Where(clause = "type = 'MUSIC_COMPOSER'")
    private List<Person> musicComposers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "movie_productionCompanies",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "production_house_id") }
    )
    private Set<ProductionHouse> productionCompanies = new HashSet<>();

    @Column
    private String releaseDate;

    @Column
    private String duration;

    @Column
    private String language;

    @Column
    private String budget;

    @Column
    private String boxOfficeCollection;

    @Column
    private int categoryCount;

    @ManyToMany
    @JoinTable(
            name = "movie_categories",
            joinColumns = { @JoinColumn(name = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories = new HashSet<>();
}
