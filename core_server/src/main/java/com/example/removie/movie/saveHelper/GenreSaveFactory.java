package com.example.removie.movie.saveHelper;

import com.example.removie.movie.entityMapper.GenreEntityMapper;
import com.example.removie.movie.fetcher.MovieDataFetcher;
import com.example.removie.movie.jpaManager.GenreJpaManager;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 영화 장르정보를 저장합니다.
 * 이 클래스는 다음과 같은 구성 요소를 활용합니다:
 * - {@link GenreJpaManager}:  데이터베이스 저장 작업을 처리합니다.
 * - {@link MovieDataFetcher}: 외부 또는 내부 API를 통해 영화 정보를 추출,
 */

@Component
public class GenreSaveFactory implements SaveHelperFactory{
    private final GenreJpaManager genreJpaManager;
    private final MovieDataFetcher movieDataFetcher;

    @Autowired
    public GenreSaveFactory(GenreJpaManager genreJpaManager, MovieDataFetcher movieDataFetcher) {
        this.genreJpaManager = genreJpaManager;
        this.movieDataFetcher = movieDataFetcher;
    }

    @Override
    public @Nonnull SaveHelper createSaveHelper() {
        return SaveHelperBuilder.of(genreJpaManager, GenreEntityMapper.getGenreEntity(movieDataFetcher.fetchMovieData()));
    }
}
