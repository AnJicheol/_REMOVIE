package com.example.removie.movie.vo;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;


@Getter
public class ReleaseDate {

    @NotBlank
    private final String movieCode;
    private final LocalDate releaseDate;

    public ReleaseDate(@NonNull String movieCode, @NonNull LocalDate releaseDate1) {
        this.movieCode = movieCode;
        this.releaseDate = releaseDate1;
    }


}
