package com.example.removie.movie.entityMapper;


import com.example.removie.movie.entity.NewMovieEntity;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public class NewMovieEntityMapper {
    public static @Nonnull List<NewMovieEntity> getNewMovieEntity(@NotNull List<String> movieCodeList, @NotNull Integer version){
        return movieCodeList.stream()
                .map(movieCode -> NewMovieEntity.builder()
                        .movieCode(movieCode)
                        .version(version).build())
                .toList();
    }

}