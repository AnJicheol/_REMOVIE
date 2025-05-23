package com.example.removie.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "movie_data")
public class MovieDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_title", nullable = false)
    @NotNull
    @NotEmpty
    private String title;

    @Column(name = "movie_code", nullable = false, unique = true)
    @NotNull
    @NotEmpty
    private String movieCode;

    @Builder
    public MovieDataEntity(@NonNull String title, @NonNull String movieCode) {
        this.title = title;
        this.movieCode = movieCode;
    }
}
