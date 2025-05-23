package com.example.removie.movie.entityMapper;


import com.example.removie.movie.entity.MovieDataEntity;
import com.example.removie.movie.vo.MovieData;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class MovieDataEntityMapper {

    public static @Nonnull List<MovieDataEntity> getMovieDataEntity(@NotNull List<MovieData> movieDataList) {
        return movieDataList.stream()
                .map(movieData -> MovieDataEntity.builder()
                        .title(movieData.getTitle())
                        .movieCode(movieData.getMovieCode())
                        .build()).toList();
    }
}
