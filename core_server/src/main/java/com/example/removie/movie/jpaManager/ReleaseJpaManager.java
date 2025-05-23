package com.example.removie.movie.jpaManager;


import com.example.removie.movie.entity.ReleaseEntity;
import com.example.removie.movie.vo.BasicMovieVO;
import com.example.removie.movie.repository.ReleaseRepository;
import com.example.removie.retry.IORetry;
import com.example.removie.version.VersionJpaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Component
public class ReleaseJpaManager implements UpdateJpaManager<List<ReleaseEntity>>{
    private final ReleaseRepository releaseRepository;
    private final VersionJpaManager versionJpaManager;

    @Autowired
    public ReleaseJpaManager(ReleaseRepository releaseRepository, VersionJpaManager versionJpaManager){
        this.releaseRepository = releaseRepository;
        this.versionJpaManager = versionJpaManager;
    }

    @IORetry
    @Transactional
    public List<BasicMovieVO> getLatestMovieList(){
        return releaseRepository.findByVersion(versionJpaManager.getLatestVersion());
    }

    @IORetry
    @Transactional
    public void saveCurrentMovieList(List<ReleaseEntity> releaseEntityList){
        releaseRepository.saveAll(releaseEntityList);
    }

    @Override
    @Transactional
    public void update(List<ReleaseEntity> releaseEntityList) {
        saveCurrentMovieList(releaseEntityList);
    }
}
