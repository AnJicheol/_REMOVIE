package com.example.removie.kobis.wrapper;

import com.example.removie.movie.vo.MovieData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MovieDataWrapper {

    private final MovieInfoResult movieInfoResult;

    public MovieDataWrapper(@JsonProperty(...blind...) MovieInfoResult movieInfoResult) {
        this.movieInfoResult = movieInfoResult;
    }

    public MovieData getMovieData() {
        return movieInfoResult.getMovieData();
    }
}
