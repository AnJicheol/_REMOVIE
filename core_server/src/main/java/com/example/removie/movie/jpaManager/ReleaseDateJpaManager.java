package com.example.removie.movie.jpaManager;

import com.example.removie.movie.entity.ReleaseDateEntity;
import com.example.removie.movie.repository.ReleaseMovieDateRepository;
import com.example.removie.retry.IORetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ReleaseDateJpaManager implements UpdateJpaManager<List<ReleaseDateEntity>>{
    private final ReleaseMovieDateRepository releaseMovieDateRepository;

    @Autowired
    public ReleaseDateJpaManager(ReleaseMovieDateRepository releaseMovieDateRepository) {
        this.releaseMovieDateRepository = releaseMovieDateRepository;
    }

    @IORetry
    @Transactional
    public void saveReleaseDate(List<ReleaseDateEntity> releaseDateList){
        releaseMovieDateRepository.saveAll(releaseDateList);
    }

    @Override
    @Transactional
    public void update(List<ReleaseDateEntity> releaseDateList) {
        saveReleaseDate(releaseDateList);
    }
}
