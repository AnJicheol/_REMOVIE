package com.example.removie.movie.entityMapper;

import com.example.removie.movie.entity.GenreEntity;
import com.example.removie.movie.vo.MovieData;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GenreEntityMapper {

    public @Nonnull static List<GenreEntity> getGenreEntity(@NotNull List<MovieData> movieDataList){
        return movieDataList.stream()
                .flatMap(movie -> movie.getGenreVOList().stream()
                        .map(genreVO -> GenreEntity.builder()
                                .genre(genreVO.getGenreNm())
                                .movieCode(movie.getMovieCode())
                                .build()))
                .toList();
    }
}
