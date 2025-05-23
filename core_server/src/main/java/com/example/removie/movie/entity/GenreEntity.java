package com.example.removie.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "movie_genre")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genre", nullable = false)
    @NotNull
    @NotEmpty
    private String genre;

    @Column(name = "movie_code", nullable = false)
    @NotNull
    @NotEmpty
    private String movieCode;

    @Builder
    public GenreEntity(String genre, String movieCode) {
        this.genre = genre;
        this.movieCode = movieCode;
    }
}
