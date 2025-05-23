package com.example.removie.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "release_date")
public class ReleaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "movie_code", nullable = false)
    private String movieCode;

    @NotNull
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Builder
    public ReleaseDateEntity(@NonNull String movieCode, @NonNull LocalDate releaseDate) {
        this.movieCode = movieCode;
        this.releaseDate = releaseDate;
    }
}
