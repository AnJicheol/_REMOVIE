package com.example.removie.kobis.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GenreVO {
    @NotNull
    @NotEmpty
    @JsonProperty(...blind...)
    private final String genreNm;

    public GenreVO(@JsonProperty(...blind...) String genreNm) {
        this.genreNm = genreNm;
    }
}
