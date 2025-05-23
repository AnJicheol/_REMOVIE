package com.example.removie.kobis.wrapper;


import com.example.removie.movie.vo.MovieData;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MovieInfoResult {
    private final MovieData movieInfo;

    public MovieInfoResult(@JsonProperty(...blind...) MovieData movieInfo) {
        this.movieInfo = movieInfo;
    }

    public MovieData getMovieData() {
        return movieInfo;
    }
}
