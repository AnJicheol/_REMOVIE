package com.example.removie.movie.jpaManager;

import com.example.removie.movie.entity.NewMovieEntity;
import com.example.removie.movie.repository.NewMovieRepository;
import com.example.removie.retry.IORetry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class NewMovieJpaManager implements UpdateJpaManager<List<NewMovieEntity>>{
    private final NewMovieRepository newMovieRepository;

    public NewMovieJpaManager(NewMovieRepository newMovieRepository) {
        this.newMovieRepository = newMovieRepository;
    }

    @IORetry
    @Override
    @Transactional
    public void update(List<NewMovieEntity> newMovieEntities) {
        newMovieRepository.saveAll(newMovieEntities);
    }
}
