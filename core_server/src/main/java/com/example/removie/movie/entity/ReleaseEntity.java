package com.example.removie.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "release_info")
public class ReleaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @NotEmpty
    @Column(name = "movie_code", nullable = false)
    private String movieCode;

    @NotNull
    @Column(name = "ranking", nullable = false)
    private Integer ranking;

    @Builder
    public ReleaseEntity(@NonNull Integer version, @NonNull String movieCode, @NonNull Integer ranking) {
        this.version = version;
        this.movieCode = movieCode;
        this.ranking = ranking;
    }

}
