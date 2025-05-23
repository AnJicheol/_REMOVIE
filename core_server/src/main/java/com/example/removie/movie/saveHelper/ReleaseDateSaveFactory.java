package com.example.removie.movie.saveHelper;

import com.example.removie.movie.entityMapper.ReleaseDateEntityMapper;
import com.example.removie.movie.fetcher.ReleaseDateFetcher;
import com.example.removie.movie.jpaManager.ReleaseDateJpaManager;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 영화 개봉일 정보를 저장하기 위한 {@link SaveHelper} 인스턴스를 생성하는 팩토리 클래스입니다.
 * <p>
 * 이 클래스는 {@link ReleaseDateFetcher}를 통해 수집한 개봉일 데이터를 기반으로,
 * {@link ReleaseDateJpaManager}를 통해 해당 데이터를 저장하는 {@code SaveHelper}를 구성합니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class ReleaseDateSaveFactory implements SaveHelperFactory{
    private final ReleaseDateFetcher releaseDateFetcher;
    private final ReleaseDateJpaManager releaseDateJpaManager;


    @Autowired
    public ReleaseDateSaveFactory(ReleaseDateFetcher releaseDateFetcher, ReleaseDateJpaManager releaseDateJpaManager) {
        this.releaseDateFetcher = releaseDateFetcher;
        this.releaseDateJpaManager = releaseDateJpaManager;
    }

    @Override
    public @Nonnull SaveHelper createSaveHelper() {
        return SaveHelperBuilder.of(releaseDateJpaManager, ReleaseDateEntityMapper.getReleaseDateEntity(releaseDateFetcher.fetchReleaseDate()));
    }
}
