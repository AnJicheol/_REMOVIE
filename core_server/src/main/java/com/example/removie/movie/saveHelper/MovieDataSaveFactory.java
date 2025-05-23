package com.example.removie.movie.saveHelper;

import com.example.removie.movie.entityMapper.MovieDataEntityMapper;
import com.example.removie.movie.fetcher.MovieDataFetcher;
import com.example.removie.movie.jpaManager.MovieJpaManager;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 영화 데이터를 저장하기 위한 {@link SaveHelper} 인스턴스를 생성하는 팩토리 클래스입니다.
 *
 * <p>
 * 이 클래스는 {@link MovieDataFetcher}를 통해 새로운 영화 데이터를 수집하고,
 * {@link MovieJpaManager}를 통해 해당 데이터를 저장하는 {@code SaveHelper}를 구성합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class MovieDataSaveFactory implements SaveHelperFactory{
    private final MovieDataFetcher movieDataFetcher;
    private final MovieJpaManager movieJpaManager;

    @Autowired
    public MovieDataSaveFactory(MovieDataFetcher movieDataFetcher, MovieJpaManager movieJpaManager) {
        this.movieDataFetcher = movieDataFetcher;
        this.movieJpaManager = movieJpaManager;
    }

    @Override
    public @Nonnull SaveHelper createSaveHelper() {
        return SaveHelperBuilder.of(movieJpaManager,
                MovieDataEntityMapper.getMovieDataEntity(
                        movieDataFetcher.fetchMovieData()
                )
        );
    }
}
