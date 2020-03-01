package com.thinkanalytics.rks.wiki.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "productionHouse")
@Data
public class ProductionHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    /*@ManyToMany(mappedBy = "productionCompanies")
    private Set<Movie> movies = new HashSet<>();*/
}
