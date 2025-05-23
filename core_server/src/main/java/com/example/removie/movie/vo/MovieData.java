package com.example.removie.movie.vo;


import com.example.removie.kobis.wrapper.GenreVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
@Builder
public class MovieData {

    @JsonProperty(...blind...)
    private final String title;

    @JsonProperty(...blind...)
    private final String movieCode;

    @JsonProperty(...blind...)
    private final List<GenreVO> genreVOList;


    public MovieData(
            @JsonProperty(...blind...) String title,
            @JsonProperty(...blind...) String movieCode,
            @JsonProperty(...blind...) List<GenreVO> genreVOList
    ) {
        this.title = title;
        this.movieCode = movieCode;
        this.genreVOList = genreVOList;
    }

}