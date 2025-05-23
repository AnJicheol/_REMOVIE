package com.example.removie.movie.saveHelper;

import com.example.removie.movie.entityMapper.NewMovieEntityMapper;
import com.example.removie.movie.fetcher.NewMovieFetcher;
import com.example.removie.movie.jpaManager.NewMovieJpaManager;
import com.example.removie.version.VersionJpaManager;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

/**
 * 새로 개봉한 영화를 저장하기 위한 클래스입니다.
 * 이 클래스는 다음과 같은 구성 요소를 활용합니다:
 * - {@link NewMovieJpaManager}: 목록에 대한 DB 업데이트 작업을 처리합니다.
 * - {@link NewMovieFetcher}: 기존에 저장되지 않은 신규 영화 코드 목록을 가져옵니다.
 * - {@link VersionJpaManager}: 신규 영화 데이터를 저장할 때 사용할 버전을 제공합니다.
 */
@Component
public class NewMovieSaveFactory implements SaveHelperFactory{
    private final NewMovieJpaManager newMovieJpaManager;
    private final NewMovieFetcher newMovieFetcher;
    private final VersionJpaManager versionJpaManager;

    public NewMovieSaveFactory(NewMovieJpaManager newMovieJpaManager, NewMovieFetcher newMovieFetcher, VersionJpaManager versionJpaManager) {
        this.newMovieJpaManager = newMovieJpaManager;
        this.newMovieFetcher = newMovieFetcher;
        this.versionJpaManager = versionJpaManager;
    }

    @Override
    public @Nonnull SaveHelper createSaveHelper() {
        return SaveHelperBuilder.of(
                newMovieJpaManager,
                NewMovieEntityMapper.getNewMovieEntity(
                        newMovieFetcher.getNewMovieList(),
                        versionJpaManager.createNewVersion()
                )
        );
    }
}
