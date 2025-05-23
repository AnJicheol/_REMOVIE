package com.example.removie.movie.fetcher;

import com.example.removie.kobis.KOBISMovieDataService;
import com.example.removie.movie.jpaManager.MovieJpaManager;
import com.example.removie.movie.vo.MovieData;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;



/**
 * 새로운 영화 코드 목록을 조회하고, 기존에 등록된 코드만 선별하여
 * 각 영화의 상세 정보를 KOBIS API를 통해 가져오는 객체입니다.
 * <p>
 * <ul>
 *   <li>{@link NewMovieFetcher} – 새로운 영화 코드 목록을 조회합니다.</li>
 *   <li>{@link MovieJpaManager} – 이미 등록된 영화 코드인지 여부를 판단합니다.</li>
 *   <li>{@link KOBISMovieDataService} – 유효한 영화 코드에 대해 상세 정보를 외부 API에서 조회합니다.</li>
 * </ul>
 * 최종적으로 유효한 영화 코드에 대응하는 {@link MovieData} 객체 리스트를 반환합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class MovieDataFetcher {
    private final KOBISMovieDataService kobisMovieDataService;
    private final MovieJpaManager movieJpaManager;
    private final NewMovieFetcher newMovieFetcher;

    @Autowired
    public MovieDataFetcher(KOBISMovieDataService kobisMovieDataService, MovieJpaManager movieJpaManager, NewMovieFetcher newMovieFetcher) {
        this.kobisMovieDataService = kobisMovieDataService;
        this.movieJpaManager = movieJpaManager;
        this.newMovieFetcher = newMovieFetcher;
    }

    @Cacheable(value = "MovieDataListF")
    public @Nonnull List<MovieData> fetchMovieData() {
        return newMovieFetcher.getNewMovieList().stream()
                .filter(movieJpaManager::notExistsByMovieCode)
                .map(kobisMovieDataService::getMovieData)
                .toList();
    }
}
