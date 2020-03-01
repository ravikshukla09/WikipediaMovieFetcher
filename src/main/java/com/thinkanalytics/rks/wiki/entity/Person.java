package com.thinkanalytics.rks.wiki.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "person",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "type", "movie_id" }) })
public class Person {
    public enum Type {
        ACTOR, DIRECTOR, PRODUCER, MUSIC_COMPOSER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie  movie;
}
