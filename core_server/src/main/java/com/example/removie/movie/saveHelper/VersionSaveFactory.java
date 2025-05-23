package com.example.removie.movie.saveHelper;

import com.example.removie.version.VersionJpaManager;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;


/**
 * 버전 정보를 저장하기 위한 {@link SaveHelper} 인스턴스를 생성하는 팩토리 클래스입니다.
 * <p>
 * 이 클래스는 {@link VersionJpaManager}로부터 최신 버전 정보를 조회하고,
 * 해당 버전을 저장할 수 있는 {@code SaveHelper}를 생성합니다.
 * </p>
 *
 * <p>
 * 생성된 {@link SaveHelper}는 버전 동기화 과정에서
 * 최신 버전 값을 기록하거나 업데이트하는 데 사용됩니다.
 * </p>
 *
 * @author An_Jicheol
 * @version 2.0
 */
@Component
public class VersionSaveFactory implements SaveHelperFactory{
    private final VersionJpaManager versionJpaManager;

    public VersionSaveFactory(VersionJpaManager versionJpaManager) {
        this.versionJpaManager = versionJpaManager;
    }

    @Override
    public @Nonnull SaveHelper createSaveHelper() {
        return SaveHelperBuilder.of(versionJpaManager, versionJpaManager.createNewVersion());
    }
}
