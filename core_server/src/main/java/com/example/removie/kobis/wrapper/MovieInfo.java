package com.example.removie.kobis.wrapper;

import com.example.removie.movie.vo.MovieData;
import lombok.Getter;

@Getter
public class MovieInfo {
    private final MovieData movieData;

    public MovieInfo(MovieData movieData) {
        this.movieData = movieData;
    }
}
