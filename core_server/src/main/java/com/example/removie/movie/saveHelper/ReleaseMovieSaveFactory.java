package com.example.removie.movie.saveHelper;

import com.example.removie.kobis.NewReleasesService;
import com.example.removie.movie.entityMapper.ReleaseEntityMapper;
import com.example.removie.movie.jpaManager.ReleaseJpaManager;
import com.example.removie.version.VersionJpaManager;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 상영 중인 영화 목록 정보를 저장하기 위한 {@link SaveHelper} 인스턴스를 생성하는 팩토리 클래스입니다.
 * <p>
 * 이 클래스는 {@link NewReleasesService}를 통해 현재 상영 중인 영화 목록을 조회하고,
 * {@link VersionJpaManager}를 통해 최신 버전을 불러온 뒤, 이를 기반으로 {@code ReleaseVO}를 구성합니다.
 * 이렇게 생성된 {@code ReleaseVO}는 {@link ReleaseJpaManager}를 통해 저장됩니다.
 * </p>
 *
 * <p>
 * 저장 로직과 생성 로직을 분리함으로써 모듈화된 구조를 유지하며, 다양한 저장 전략에 유연하게 대응할 수 있습니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class ReleaseMovieSaveFactory implements SaveHelperFactory{
    private final ReleaseJpaManager releaseJpaManager;
    private final NewReleasesService newReleasesService;
    private final VersionJpaManager versionJpaManager;
    private final ReleaseEntityMapper mapper;

    @Autowired
    public ReleaseMovieSaveFactory(ReleaseJpaManager releaseJpaManager, NewReleasesService newReleasesService, VersionJpaManager versionJpaManager, ReleaseEntityMapper mapper) {
        this.releaseJpaManager = releaseJpaManager;
        this.newReleasesService = newReleasesService;
        this.versionJpaManager = versionJpaManager;
        this.mapper = mapper;
    }

    @Override
    public @Nonnull SaveHelper createSaveHelper(){
        return SaveHelperBuilder.of(
                releaseJpaManager,
                mapper.getReleaseEntityByVO(
                        newReleasesService.getReleaseMap().getBasicMovieVOList(),
                        versionJpaManager.createNewVersion()
                ));
    }
}
