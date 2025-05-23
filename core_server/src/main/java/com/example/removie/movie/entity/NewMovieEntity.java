package com.example.removie.movie.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "new_movie")
public class NewMovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_code", nullable = false)
    @NotNull
    @NotEmpty
    private String movieCode;

    @Column(nullable = false, name = "version")
    @NotNull
    private Integer version;

    @Builder
    public NewMovieEntity(@NonNull String movieCode, @NonNull Integer version) {
        this.movieCode = movieCode;
        this.version = version;
    }
}
