package com.example.removie.movie.entityMapper;

import com.example.removie.movie.entity.ReleaseDateEntity;
import com.example.removie.movie.vo.ReleaseDate;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public class ReleaseDateEntityMapper {
    public static @Nonnull List<ReleaseDateEntity> getReleaseDateEntity(@NotNull List<ReleaseDate> releaseDateList) {
        return releaseDateList.stream()
                .map(releaseDate -> ReleaseDateEntity.builder()
                        .releaseDate(releaseDate.getReleaseDate())
                        .movieCode(releaseDate.getMovieCode())
                        .build()
                ).toList();
    }
}
